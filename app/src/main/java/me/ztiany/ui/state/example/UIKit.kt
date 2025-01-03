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
 * This function encapsulates common logic for handling network request states.
 * Typically, the network request flow includes:
 *
 * 1. Initiating the network request and displaying a loading dialog.
 * 2. Displaying the result upon successful network response.
 * 3. Prompting the user in case of a network request error.
 *
 * [State] represents the request state, and each state change should notify the associated [LiveData].
 * This method subscribes to [LiveData] and handles various states.
 * Automatic handling includes displaying loading and error prompts.
 * Usually, only [StateHandlerBuilder.onSuccess] is required to handle successful network results.
 * Optionally, you can provide [StateHandlerBuilder.onError] to handle errors yourself,
 * and [StateHandlerBuilder.onLoading] to customize loading logic.
 *
 * Additionally, note that [StateHandlerBuilder.onSuccess] = [StateHandlerBuilder.onData] + [StateHandlerBuilder.onNoData].
 * Choose based on your preference.
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