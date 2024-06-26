@file:JvmName("UIKit")

package me.ztiany.ui.state.example

import androidx.lifecycle.*
import com.android.base.foundation.state.*

fun LoadingViewHost.dismissLoadingDialogDelayed(onDismiss: (() -> Unit)? = null) {
    dismissLoadingDialog(500, onDismiss)
}

/** Configure how to handle UI state [State]. */
class ResourceHandlerBuilder<L, D, E> {

    /** [onLoading] will be called once [State] is [Loading]. */
    var onLoading: ((L?) -> Unit)? = null

    /** [onError] will be called once [State] is [Error]. */
    var onError: ((Throwable, E?) -> Unit)? = null

    /** [onSuccess] will always be called once [State] is [Success]. */
    var onSuccess: ((D?) -> Unit)? = null

    /** [onData] will be called only when [State] is [Data]. */
    var onData: ((D) -> Unit)? = null

    /** [onNoData] will be called only when [State] is [NoData]. */
    var onNoData: (() -> Unit)? = null

    /** when [State] is [Loading], what to show on the loading dialog. */
    var loadingMessage: CharSequence = ""

    /** indicate whether the loading dialog should be showing when [State] is [Loading]. */
    var showLoading: Boolean = true

    /** indicate whether the loading dialog is cancelable. */
    var forceLoading: Boolean = true

    /** mark the event handled so that it will not be handled again. refer [ViewModel One-off event antipatterns](https://manuelvivo.dev/viewmodel-events-antipatterns) for more details. */
    var handleAsEvent: Boolean = true
}

/**
 * 这是一个网络请求状态转换处理的通用逻辑封装，一般情况下，网络请求流程为：
 *
 * 1. 发起网络请求，展示 loading 对话框。
 * 2. 网络请求正常返回，则展示调用结果。
 * 3. 网络请求发送错误，则提示用户请求错误。
 *
 * [State] 表示请求状态，每次状态变更，[LiveData] 都应该进行通知，该方法订阅 [LiveData] 并对各种状态进行处理。
 * 展示 loading 和对错误进行提示都是自动进行的，通常情况下，只需要提供 [ResourceHandlerBuilder.onSuccess] 对正常的网络结果进行处理即可。
 * 当然如果希望自己处理错误，则可以提供 [ResourceHandlerBuilder.onError] 回调。如果希望自己处理加载中的逻辑，则可以提供 [ResourceHandlerBuilder.onLoading] 回调。
 *
 * 另外需要注意的是：[ResourceHandlerBuilder.onSuccess] =  [ResourceHandlerBuilder.onData] + [ResourceHandlerBuilder.onNoData]，请根据你的偏好进行选择。
 */
fun <H, L, D, E> H.handleLiveData(
    data: LiveData<State<L, D, E>>,
    handlerBuilder: ResourceHandlerBuilder<L, D, E>.() -> Unit,
) where H : LoadingViewHost, H : LifecycleOwner {
    val builder = ResourceHandlerBuilder<L, D, E>()
    handlerBuilder(builder)

    data.observe(this) { state ->
        handleResourceInternal(state, builder)
    }
}

/** refer to [handleLiveData]. */
fun <H, L, D, E> H.handleResource(
    state: State<L, D, E>,
    handlerBuilder: ResourceHandlerBuilder<L, D, E>.() -> Unit,
) where H : LoadingViewHost, H : LifecycleOwner {
    val builder = ResourceHandlerBuilder<L, D, E>()
    handlerBuilder(builder)
    handleResourceInternal(state, builder)
}

private fun <H, L, D, E> H.handleResourceInternal(
    state: State<L, D, E>,
    handlerBuilder: ResourceHandlerBuilder<L, D, E>,
) where H : LoadingViewHost, H : LifecycleOwner {

    when (state) {
        is Idle -> {}

        //----------------------------------------loading start
        // The loading state should always be handled, so we ignore the clearAfterHanded config here.
        is Loading -> {
            if (handlerBuilder.showLoading) {
                if (handlerBuilder.onLoading == null) {
                    showLoadingDialog(handlerBuilder.loadingMessage, !handlerBuilder.forceLoading)
                } else {
                    handlerBuilder.onLoading?.invoke(state.step)
                }
            }
        }
        //----------------------------------------loading end

        //----------------------------------------error start
        is Error -> {
            if (state.isHandled) {
                return
            }
            if (handlerBuilder.handleAsEvent) {
                state.markAsHandled()
            }

            dismissLoadingDialogDelayed {
                val onError = handlerBuilder.onError
                if (onError != null) {
                    onError(state.error, state.reason)
                } else {
                    showMessage(convert(state.error))
                }
            }
        }
        //----------------------------------------error end

        //----------------------------------------success start
        is Success<D> -> {
            if (state.isHandled) {
                return
            }
            if (handlerBuilder.handleAsEvent) {
                state.markAsHandled()
            }

            dismissLoadingDialogDelayed {
                when (state) {
                    is NoData -> {
                        handlerBuilder.onSuccess?.invoke(null)
                        handlerBuilder.onNoData?.invoke()
                    }

                    is Data<D> -> {
                        handlerBuilder.onSuccess?.invoke(state.value)
                        handlerBuilder.onData?.invoke(state.value)
                    }
                }
            }
        }
        //----------------------------------------success end

    }
}

fun convert(error: Throwable): CharSequence {
    return ""
}