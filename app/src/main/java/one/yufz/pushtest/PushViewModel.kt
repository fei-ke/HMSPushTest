package one.yufz.pushtest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.util.AGCUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PushViewModel(val app: Application) : AndroidViewModel(app) {
    private val _tokenFlow = MutableStateFlow("")
    val tokenFlow: StateFlow<String> = _tokenFlow

    fun requestGetToken() {
        viewModelScope.launch(Dispatchers.IO){
            val token = HmsInstanceId.getInstance(app).getToken(AGCUtils.getAppId(app), "HCM")
            _tokenFlow.emit(token)
        }
    }

    fun requestDeleteToken() {
        viewModelScope.launch(Dispatchers.IO) {
            HmsInstanceId.getInstance(app).deleteToken(AGCUtils.getAppId(app), "HCM")
            _tokenFlow.emit("")
        }
    }
}