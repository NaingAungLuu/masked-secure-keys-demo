package co.codigo.demo.maskedkeys

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.buildSpannedString
import co.codigo.demo.maskedkeys.ui.theme.MaskedKeysDemoTheme
import kotlin.reflect.full.hasAnnotation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val secureKeysMap = MaskedConfig::class.members
            // Filtering out the properties marked as static by plugin
            .filter { it.isFinal && it.hasAnnotation<JvmStatic>()}
            .associate {
                val value = it.call(MaskedConfig).toString()
                it.name to value
            }

        setContent {
            MaskedKeysDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        KeyValueLayout(secureKeysMap)
                    }
                }
            }
        }
    }
}

@Composable
fun KeyValueLayout(list: Map<String,String>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .systemBarsPadding()
            .padding(20.dp)
    ) {
        items(list.toList(), key = { it.first }) { pair ->
            KeyValuePair(pair.first, pair.second)
        }
    }
}

@Composable
fun KeyValuePair(key: String, value: String) {
    // Text Composable with spannable string using different colors for key and value parameters
    Text(
        buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Red)) {
                append(key)
            }
            append(" : ")
            withStyle(SpanStyle(color = Color.Blue)) {
                append(value)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaskedKeysDemoTheme {

    }
}