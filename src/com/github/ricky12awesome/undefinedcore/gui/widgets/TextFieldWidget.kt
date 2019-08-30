package com.github.ricky12awesome.undefinedcore.gui.widgets

import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer

typealias MCTextFieldWidget = net.minecraft.client.gui.widget.TextFieldWidget

fun Widget.textField(
  text: String,
  offsetX: Int = 0,
  offsetY: Int = 0,
  width: Int = 18 * 5,
  height: Int = 18,
  font: TextRenderer = MinecraftClient.getInstance().textRenderer,
  apply: TextFieldWidget.() -> Unit = {}
) = TextFieldWidget(this, text, offsetX, offsetY, width, height, font).apply(apply).also(::addWidget)

class TextFieldWidget(
  override val parent: Widget?,
  text: String,
  offsetX: Int,
  offsetY: Int,
  width: Int,
  height: Int,
  font: TextRenderer
) : MCTextFieldWidget(font, 0, 0, width, height, text), Widget {
  override val children: MutableList<Widget> = mutableListOf()
  override val pos: WidgetPos = pos(offsetX, offsetY)
  override val size: WidgetSize = size(width, height)

  override fun resize() {
    width = size.width
  }

  override fun reposition() {
    x = (this as Widget).x
    y = (this as Widget).y

    super.reposition()
  }

  override fun render(mx: Int, my: Int, pt: Float) {
    super<MCTextFieldWidget>.render(mx, my, pt)
    super<Widget>.render(mx, my, pt)
  }
}