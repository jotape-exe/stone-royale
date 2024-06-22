package com.joaoxstone.stoneroyale.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R

val montserrat = FontFamily(
    Font(R.font.montserrat_black, FontWeight.Black),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_semi_bold, FontWeight.SemiBold),
    Font(R.font.montserrat_thin, FontWeight.Thin),
    //Italic
    Font(R.font.montserrat_italic, FontWeight.Normal, style = FontStyle.Italic),
    Font(
        R.font.montserrat_extra_light_italic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),
    Font(R.font.montserrat_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.montserrat_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.montserrat_extra_bold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),

    //Light
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_extra_light, FontWeight.ExtraLight),
    Font(R.font.montserrat_extra_light_italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),

    //Black

    //BOld

    //Medium

    //SemiBold

    //This


)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

