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
        TODO()
    }

    override fun showLoadingDialog(cancelable: Boolean): Dialog {
        TODO()
    }

    override fun showLoadingDialog(message: CharSequence, cancelable: Boolean): Dialog {
        TODO()
    }

    override fun showLoadingDialog(messageId: Int, cancelable: Boolean): Dialog {
        TODO()
    }

    override fun dismissLoadingDialog() {
        
    }

    override fun dismissLoadingDialog(minimumMills: Long, onDismiss: (() -> Unit)?) {
        
    }

    override fun isLoadingDialogShowing(): Boolean {
        return true
    }

    override fun showMessage(message: CharSequence) {
        
    }

    override fun showMessage(messageId: Int) {
        
    }

}