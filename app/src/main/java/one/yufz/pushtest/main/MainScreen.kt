@file:OptIn(ExperimentalMaterial3Api::class)

package one.yufz.pushtest.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import one.yufz.pushtest.R
import one.yufz.pushtest.ui.theme.AppTheme

@Composable
fun MainScreen() {
    AppTheme {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
        }) {
            val viewModel: PushViewModel = viewModel()

            val token by viewModel.tokenState.collectAsState()
            val loading by viewModel.loadingState.collectAsState()

            Box(modifier = Modifier.padding(it)) {
                Column() {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
                    ) {
                        ElevatedButton(onClick = { viewModel.requestGetToken() }) {
                            Text(text = "注册推送")
                        }
                        ElevatedButton(onClick = { viewModel.requestDeleteToken() }) {
                            Text(text = "取消注册")
                        }
                    }
                    SelectionContainer(modifier = Modifier.padding(all = 16.dp)) {
                        Text(text = token)
                    }
                }
                if (loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}