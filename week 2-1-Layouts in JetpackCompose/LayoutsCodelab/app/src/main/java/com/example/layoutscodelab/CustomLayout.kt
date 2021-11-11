package com.example.layoutscodelab

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.layoutscodelab.ui.theme.LayoutsCodelabTheme

fun Modifier.firstBaselineToTop(
    firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        // top-left is (0, 0)
        // padding 길이를 text를 포함하여 정하고 싶을 때.
        // placeable(text)의 Y 값은 padding의 px값 빼기 text의 기준선 y 값.
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline

        // 구해진 placeableY을 통해 Composable의 height를 구한다. placeable의 높이와 placeableY=padding값.
        val height = placeable.height + placeableY
//        Log.d("check",
//            "firstBaselineToTop.roundToPx() : "+firstBaselineToTop.roundToPx() +
//                    "  firstBaseline : "+firstBaseline
//                    +"  placeableY : "+placeableY
//        +" height : "+height
//        )
        // Log Result. firstBaselineToTop.roundToPx() : 96  firstBaseline : 77  placeableY : 19 height : 116
        // placeable의 width와 height 만큼 layout을 자리잡고, placeable을 위치시킨다.
        layout(placeable.width, height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }
)

@Preview(showBackground = true)
@Composable
fun TextWithPaddingToBaselinePreview() {
    LayoutsCodelabTheme {
        Text("Hi there!", Modifier.firstBaselineToTop(32.dp), fontSize = 24.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithNormalPaddingPreview() {
    LayoutsCodelabTheme {
        Text("Hi there!", Modifier.padding(top = 32.dp), fontSize = 24.sp)
    }
}