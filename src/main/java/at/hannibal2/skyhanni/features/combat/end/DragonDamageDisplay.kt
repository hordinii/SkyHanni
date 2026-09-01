package at.hannibal2.skyhanni.features.combat.end

import at.hannibal2.skyhanni.SkyHanniMod
import at.hannibal2.skyhanni.api.event.HandleEvent
import at.hannibal2.skyhanni.data.IslandType
import at.hannibal2.skyhanni.data.model.TabWidget
import at.hannibal2.skyhanni.events.GuiRenderEvent
import at.hannibal2.skyhanni.events.IslandChangeEvent
import at.hannibal2.skyhanni.events.WidgetUpdateEvent
import at.hannibal2.skyhanni.skyhannimodule.SkyHanniModule
import at.hannibal2.skyhanni.utils.ChatUtils
import at.hannibal2.skyhanni.utils.PlayerUtils
import at.hannibal2.skyhanni.utils.RegexUtils.matchMatcher
import at.hannibal2.skyhanni.utils.RenderUtils.renderRenderables
import at.hannibal2.skyhanni.utils.renderables.Renderable
import at.hannibal2.skyhanni.utils.renderables.primitives.text

@SkyHanniModule
object DragonDamageDisplay {

    private val config get() = SkyHanniMod.feature.combat.endIsland.dragon

    // Reuses the exact same pattern DragonFeatures already relies on for its own place/damage
    // tracking, instead of a second copy that would have to be fixed twice.
    private val tabDamagePattern get() = DragonFeatures.tabDamagePattern

    private var display = listOf<Renderable>()

    private fun displayIsEnabled() = config.damageList

    @HandleEvent(onlyOnIsland = IslandType.THE_END)
    private fun onTabList(event: WidgetUpdateEvent) {
        if (!event.isWidget(TabWidget.DRAGON)) return
        if (!displayIsEnabled()) return

        if (event.isClear()) {
            display = emptyList()
            return
        }

        val ownName = PlayerUtils.getName()
        val damageLines = mutableListOf<Renderable>()

        // The first line is the widget header ("Dragon: (Type)"), the damage entries follow it
        // and only appear once a player has actually dealt damage to the dragon.
        for (i in 1 until event.cleanLines.size) {
            val line = event.cleanLines[i]
            tabDamagePattern.matchMatcher(line) {
                val name = group("name")
                val damage = group("damage")
                val nameColor = if (name == ownName) "§b" else "§7"
                damageLines.add(Renderable.text("§8${damageLines.size + 1}. $nameColor$name§7: §a$damage"))
            } ?: ChatUtils.consoleLog("[DragonDamageDisplay] unmatched tab line: '$line'")
        }

        // Don't render a lone header while nobody has dealt damage yet.
        display = if (damageLines.isEmpty()) emptyList()
        else listOf(Renderable.text("§6§lDragon Damage")) + damageLines
    }

    @HandleEvent(onlyOnIsland = IslandType.THE_END)
    private fun onRender(event: GuiRenderEvent) {
        if (!displayIsEnabled()) return
        config.damageListPosition.renderRenderables(display, posLabel = "Dragon Damage List")
    }

    @HandleEvent
    private fun onIslandChange(event: IslandChangeEvent) {
        display = emptyList()
    }
}
