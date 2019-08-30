package com.github.ricky12awesome.undefinedcore.gui.widgets

import net.minecraft.client.gui.Drawable
import kotlin.properties.Delegates

val Widget.x get() = (parent?.pos?.x ?: 0) + pos.x
val Widget.y get() = (parent?.pos?.y ?: 0) + pos.y

fun Widget.pos(x: Int, y: Int) =
  WidgetPos(this, x, y)
fun Widget.size(width: Int, height: Int) =
  WidgetSize(this, width, height)

fun Widget.addWidget(widget: Widget) {
  children.add(widget)
}

class WidgetPos(val widget: Widget, x: Int, y: Int) {
  var x: Int by Delegates.observable(x) { _, _, _ -> widget.reposition() }
  var y: Int by Delegates.observable(y) { _, _, _ -> widget.reposition() }
}

class WidgetSize(val widget: Widget, width: Int, height: Int) {
  var width: Int by Delegates.observable(width) { _, _, _ -> widget.resize() }
  var height: Int by Delegates.observable(height) { _, _, _ -> widget.resize() }
}

interface Widget : Drawable {
  val parent: Widget?
  val children: MutableList<Widget>

  val pos: WidgetPos
  val size: WidgetSize

  fun resize() {

  }

  fun reposition() {
    children.forEach {
      it.reposition()
    }
  }

  override fun render(mx: Int, my: Int, pt: Float) {
    children.forEach {
      it.render(mx, my, pt)
    }
  }
}