package com.github.ricky12awesome.fabric.uc.gui.widgets

import com.github.ricky12awesome.fabric.uc.utils.randomColor
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.Element
import kotlin.math.roundToInt

inline fun blankPanel(
  x: Int = 0,
  y: Int = 0,
  width: Int = 18 * 9,
  height: Int = 18 * 9,
  parent: Widget? = null,
  apply: BlankPanelWidget.() -> Unit
) = BlankPanelWidget(parent, x, y, width, height).apply(apply)

inline fun Widget.blankPanel(
  x: Int = 0,
  y: Int = 0,
  width: Int = 18 * 9,
  height: Int = 18 * 9,
  apply: BlankPanelWidget.() -> Unit
) = blankPanel(x, y, width, height, this, apply)

class BlankPanelWidget(
  override val parent: Widget?,
  x: Int,
  y: Int,
  width: Int,
  height: Int
) : Widget {
  override val children: MutableList<Widget> = mutableListOf()
  override val pos: WidgetPos = pos(x, y)
  override val size: WidgetSize = size(width, height)
  private val color = randomColor(a = 64..128)

  override fun render(mx: Int, my: Int, pt: Float) {
    DrawableHelper.fill(x, y, x + size.width, y + size.height, color.rgb)

    super.render(mx, my, pt)
  }
}