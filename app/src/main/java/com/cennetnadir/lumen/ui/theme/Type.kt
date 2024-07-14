package com.cennetnadir.lumen.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        color = Color.White // Set the default font color to white
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp,
        color = Color.White // Set the default font color to white
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        color = Color.White // Set the default font color to white
    )
    // You can override other text styles here as well
)
