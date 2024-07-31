import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

data class City(
    val name: String,
    val timeZone: TimeZone,
)

@Composable
@Preview
fun App() {
    MaterialTheme {
        val city = remember {
            listOf(
                City("Moscow", TimeZone.of("Europe/Moscow")),
                City("Novosibirsk", TimeZone.of("Asia/Novosibirsk")),
                City("Irkutsk-Ude", TimeZone.of("Asia/Irkutsk")),
                City("Vladivostok", TimeZone.of("Asia/Vladivostok")),
                City("London", TimeZone.of("Europe/London")),
            )
        }

        var cityTimes by remember {
            mutableStateOf(
                listOf<Pair<City, LocalDateTime>>()
            )
        }

        LaunchedEffect(true) {
            while (true) {
                cityTimes = city.map {
                    val now = Clock.System.now()
                    it to now.toLocalDateTime(it.timeZone)
                }
                delay(1000L)
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(cityTimes) { (city, dataTime) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = city.name,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        fontWeight = MaterialTheme.typography.body1.fontWeight,
                    )
                    Column(
                        horizontalAlignment = Alignment.End,
                    ) {
                        Text(
                            text = dataTime
                                .format(
                                    LocalDateTime.Format {
                                        hour()
                                        char(':')
                                        minute()
                                        char(':')
                                        second()
                                    }
                                ),
                            fontSize = MaterialTheme.typography.h3.fontSize,
                            fontWeight = MaterialTheme.typography.h3.fontWeight,
                        )
                        Text(
                            text = dataTime
//                                .toInstant(city.timeZone)
//                                .plus(20.hours)
//                                .toLocalDateTime(city.timeZone)
                                .format(
                                    LocalDateTime.Format {
                                        dayOfMonth()
                                        char('/')
                                        monthNumber()
                                        char('/')
                                        year()
                                    }
                                ),
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            fontWeight = MaterialTheme.typography.body2.fontWeight,
                        )
                    }
                }
            }
        }
    }
}