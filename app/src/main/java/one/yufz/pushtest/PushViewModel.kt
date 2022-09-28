package one.yufz.pushtest

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.huawei.hms.common.util.AGCUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PushViewModel(val app: Application) : AndroidViewModel(app) {
    companion object {
        private const val TAG = "PushViewModel"
    }

    private val _tokenFlow = MutableStateFlow("")
    val tokenFlow: StateFlow<String> = _tokenFlow

    fun requestGetToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val token = HmsInstanceId.getInstance(app).getToken(AGCUtils.getAppId(app), "HCM")
                Log.i(TAG, "requestGetToken: token = $token")
                _tokenFlow.emit(token)
            } catch (e: ApiException) {
                Log.e(TAG, "requestGetToken: error", e)
                _tokenFlow.emit(e.toString())
            }
        }
    }

    fun requestDeleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            HmsInstanceId.getInstance(app).deleteToken(AGCUtils.getAppId(app), "HCM")
            _tokenFlow.emit("")
        }
    }
}