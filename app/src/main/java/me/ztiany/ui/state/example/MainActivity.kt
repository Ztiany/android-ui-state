package me.ztiany.ui.state.example

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity(), LoadingViewHost {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(DebugTree())

        handleLiveData(TestViewModel().gameDetailState) {
            onSuccess = {

            }
            onLoading = {

            }
            onError = { error, reason ->

            }
        }

        handleLiveData(TestViewModel().gameDetailState1) {
            onSuccess = {

            }

            onError = { error, _ ->

            }
        }
    }

    override fun showLoadingDialog(): Dialog {
        TODO("Not yet implemented")
    }

    override fun showLoadingDialog(cancelable: Boolean): Dialog {
        TODO("Not yet implemented")
    }

    override fun showLoadingDialog(message: CharSequence, cancelable: Boolean): Dialog {
        TODO("Not yet implemented")
    }

    override fun showLoadingDialog(messageId: Int, cancelable: Boolean): Dialog {
        TODO("Not yet implemented")
    }

    override fun dismissLoadingDialog() {
        TODO("Not yet implemented")
    }

    override fun dismissLoadingDialog(minimumMills: Long, onDismiss: (() -> Unit)?) {
        TODO("Not yet implemented")
    }

    override fun isLoadingDialogShowing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun showMessage(message: CharSequence) {
        TODO("Not yet implemented")
    }

    override fun showMessage(messageId: Int) {
        TODO("Not yet implemented")
    }

}