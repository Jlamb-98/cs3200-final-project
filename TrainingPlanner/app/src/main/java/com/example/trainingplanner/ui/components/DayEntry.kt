package com.example.trainingplanner.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.trainingplanner.ui.models.DayInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DayEntry(dayInfo: DayInfo, onDayInfoChange: (DayInfo) -> Unit) {
    Text(dayInfo.day, style = MaterialTheme.typography.headlineSmall)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = dayInfo.restDay,
            onCheckedChange = { onDayInfoChange(dayInfo.copy(restDay = it)) }
        )
        Text(text = "Rest Day")
    }
    if (!dayInfo.restDay) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                value = dayInfo.type,
                onValueChange = { onDayInfoChange(dayInfo.copy(type = it)) },
                placeholder = { Text("") },
                label = { Text("Type") },
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                value = dayInfo.unit,
                onValueChange = { onDayInfoChange(dayInfo.copy(unit = it)) },
                placeholder = { Text("") },
                label = { Text("Units") },
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                value = dayInfo.startAmount,
                onValueChange = { onDayInfoChange(dayInfo.copy(startAmount = it)) },
                placeholder = { Text("") },
                label = { Text("Amount") },
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Increase by")
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                value = dayInfo.stepAmount,
                onValueChange = { onDayInfoChange(dayInfo.copy(stepAmount = it)) },
                placeholder = { Text("") },
                label = { Text("") },
            )
            Text(" every ")
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .weight(1f),
                value = dayInfo.stepFrequency,
                onValueChange = { onDayInfoChange(dayInfo.copy(stepFrequency = it)) },
                placeholder = { Text("") },
                label = { Text("") },
            )
            Text("week(s)")
        }
        Divider(modifier = Modifier.padding(8.dp))
    }
}