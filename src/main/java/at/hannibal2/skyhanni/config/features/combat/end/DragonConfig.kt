package at.hannibal2.skyhanni.config.features.combat.end

import at.hannibal2.skyhanni.config.FeatureToggle
import at.hannibal2.skyhanni.config.core.config.Position
import com.google.gson.annotations.Expose
import io.github.notenoughupdates.moulconfig.annotations.Accordion
import io.github.notenoughupdates.moulconfig.annotations.ConfigEditorBoolean
import io.github.notenoughupdates.moulconfig.annotations.ConfigLink
import io.github.notenoughupdates.moulconfig.annotations.ConfigOption

class DragonConfig {
    @Expose
    @ConfigOption(name = "Dragon Profit Tracker", desc = "")
    @Accordion
    val dragonProfitTracker: DragonProfitTrackerConfig = DragonProfitTrackerConfig()

    @Expose
    @ConfigOption(name = "Superior Notification", desc = "Show a title when a Superior Dragon spawns.")
    @ConfigEditorBoolean
    @FeatureToggle
    var superiorNotify: Boolean = true

    @Expose
    @ConfigOption(
        name = "Weight HUD",
        desc = "Shows your current dragon weight on the HUD and if hovered shows the breakdown." +
            " The dragon widget needs to be enabled for this to work."
    )
    @ConfigEditorBoolean
    @FeatureToggle
    var display: Boolean = true

    @Expose
    @ConfigLink(owner = DragonConfig::class, field = "display")
    val displayPosition: Position = Position(120, 40)

    @Expose
    @ConfigOption(
        name = "Damage List HUD",
        desc = "Shows a list of all damage sources and their damage during the dragon fight." +
            " The dragon widget needs to be enabled for this to work."
    )
    @ConfigEditorBoolean
    @FeatureToggle
    var damageList: Boolean = true

    @Expose
    @ConfigLink(owner = DragonConfig::class, field = "damageList")
    val damageListPosition: Position = Position(120, 150)

    @Expose
    @ConfigOption(name = "Weight Message", desc = "Shows your dragon weight in chat after the dragon died.")
    @ConfigEditorBoolean
    @FeatureToggle
    var chat: Boolean = true

    @Expose
    @ConfigOption(name = "Skyhanni Prefix", desc = "Displays the Skyhanni prefix in the dragon weight message.")
    @ConfigEditorBoolean
    @FeatureToggle
    var skyhanniMessagePrefix: Boolean = true
}
