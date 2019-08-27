package com.github.ricky12awesome.fabric.uc.gui

import com.github.ricky12awesome.fabric.uc.gui.widgets.Widget
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

abstract class KScreen(title: Text) : Screen(title) {
  protected lateinit var root: Widget
  abstract fun root(): Widget

  fun Widget.screenCenter() {
    pos.x = (width / 2) - (size.width / 2)
    pos.y = (height / 2) - (size.height / 2)
  }

  override fun init(mc: MinecraftClient?, x: Int, y: Int) {
    super.init(mc, x, y)

    root = root().also(Widget::reposition)

    if (root is Element) {
      children.add(root as Element)
    }

    recurseAdd(root.children)
  }

  private fun recurseAdd(children: List<Widget>) {
    children.forEach {
      if (it is Element) {
        this.children.add(it)
      }

      recurseAdd(it.children)
    }
  }

  override fun render(mx: Int, my: Int, pt: Float) {
    root.render(mx, my, pt)
  }

}