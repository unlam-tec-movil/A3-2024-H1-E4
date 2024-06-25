package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R

@Preview
@Composable
fun


        AnimatedAchievementRow(
    achievements: List<Triple<String, String, Int>> =
        listOf(
            Triple("Kilómetros", "4KM/10KM", R.drawable.shoe_prints),
            Triple("Calorías", "22 dias", R.drawable.flame),
            Triple("Minutos", "500 kcal", R.drawable.clock_lines),
            Triple("Días", "10KM", R.drawable.calendar_days),
        ),
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
    ) {
        items(achievements.size) { item ->
            CardAward(achievements[item].first, achievements[item].second, achievements[item].third)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
        }
    }
}
