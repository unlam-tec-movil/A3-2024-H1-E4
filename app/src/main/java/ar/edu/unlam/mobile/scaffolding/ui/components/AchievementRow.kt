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
import ar.edu.unlam.mobile.scaffolding.domain.MockEntities
import ar.edu.unlam.mobile.scaffolding.domain.models.User

@Preview
@Composable
fun AchievementRow(user: User = MockEntities.user) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
    ) {
        items(1) {
            CardAward("Kilómetros", user.kilometers.toString(), R.drawable.shoe_prints)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            CardAward("Calorías", user.calories.toString(), R.drawable.flame)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            CardAward("Minutos", user.minutes.toString(), R.drawable.clock_lines)
            Spacer(modifier = Modifier.padding(horizontal = 5.dp))
            CardAward("Días", user.days.toString(), R.drawable.calendar_days)
        }
    }
}
