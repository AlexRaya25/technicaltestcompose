package com.rayadev.presentation.ui.components.UserDetailScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PhoneIphone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UserDetailsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        UserDetailRow(Icons.Default.PhoneIphone, "Teléfono", "+34 123 456 789")
        Spacer(modifier = Modifier.height(16.dp))
        UserDetailRow(Icons.Default.LocationOn, "Ubicación", "Madrid, España")
        Spacer(modifier = Modifier.height(16.dp))
        UserDetailRow(Icons.Default.Apartment, "Compañía", "Rayadev")
    }
}