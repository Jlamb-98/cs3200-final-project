package com.example.trainingplanner.ui.components

import android.media.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trainingplanner.ui.theme.TrainingPlannerTheme

@Composable
fun MemberItem(
    username: String
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
//            .fillMaxWidth()
            .clickable { /*TODO*/ }
            .padding(8.dp)
//            .background(MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Member Icon", modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(username, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Preview
@Composable
fun MemberItemPreview() {
    TrainingPlannerTheme {
        MemberItem(username = "BillyBob")
    }
}