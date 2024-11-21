package com.rayadev.presentation.ui.components.userDetailScreen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rayadev.presentation.R

@Composable
fun UserDetailsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        UserDetailRow(Icons.Default.PhoneIphone, stringResource(id = R.string.telephone), stringResource(id = R.string.telephone_txt))
        Spacer(modifier = Modifier.height(16.dp))
        UserDetailRow(Icons.Default.LocationOn, stringResource(id = R.string.location), stringResource(id = R.string.location_txt))
        Spacer(modifier = Modifier.height(16.dp))
        UserDetailRow(Icons.Default.Apartment, stringResource(id = R.string.company), stringResource(id = R.string.company_txt))
    }
}