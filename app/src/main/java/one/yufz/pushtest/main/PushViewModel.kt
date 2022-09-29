package one.yufz.pushtest.main

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
    val tokenState: StateFlow<String> = _tokenFlow

    private val _loadingFlow = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingFlow

    fun requestGetToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadingFlow.emit(true)
                val token = HmsInstanceId.getInstance(app).getToken(AGCUtils.getAppId(app), "HCM")
                Log.i(TAG, "requestGetToken: token = $token")
                _tokenFlow.emit(token)
            } catch (e: ApiException) {
                Log.e(TAG, "requestGetToken: error", e)
                _tokenFlow.emit(e.toString())
            } finally {
                _loadingFlow.emit(false)
            }
        }
    }

    fun requestDeleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                HmsInstanceId.getInstance(app).deleteToken(AGCUtils.getAppId(app), "HCM")
                _tokenFlow.emit("")
            } catch (e: ApiException) {
                Log.e(TAG, "requestDeleteToken: error", e)
                _tokenFlow.emit(e.toString())
            } finally {
                _loadingFlow.emit(false)
            }
        }
    }
}