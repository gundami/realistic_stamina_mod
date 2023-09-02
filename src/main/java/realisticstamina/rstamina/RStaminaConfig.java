package realisticstamina.rstamina;

import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

public class RStaminaConfig extends Config {

    @ConfigEntry(comment = "Maximum stamina of players. (Default: 64.0)")
    public double totalStamina = 64.0;
    @ConfigEntry(comment = "Energy gained every tick of resting. (Default: 0.002)")
    public double restingEnergyGainTick = 0.002;
    @ConfigEntry(comment = "If enabled you can gain energy by sitting on things. (Default: true)")
    public boolean enableResting = true;
    @ConfigEntry(comment = "Whether or not you rest when riding a horse, donkey or mule (Default: true)")
    public boolean restRidingHorse = true;
    @ConfigEntry(comment = "Whether or not you rest while moving your boat (Default: false)")
    public boolean restWhileBoatMoving = false;
    @ConfigEntry(comment = "Maximum energy that you can gain from resting. Resets when you sleep. (Default: 5.0)")
    public double maxRestingEnergyGain = 5.0;
    @ConfigEntry(comment = "X coordinate of stamina and energy hud. 0 is farthest left. (Default: 10)")
    public int hudX =  10;
    @ConfigEntry(comment = "Y coordinate of stamina and energy hud. 0 is top of the screen. (Default: 25)")
    public int hudY = 25;
    @ConfigEntry(comment = "Whether or not block breaking uses your stamina. Using a tool with efficiency will still stop stamina from being used. (Default: true)")
    public boolean breakingBlocksUsesStamina = true;
    @ConfigEntry(comment = "When enabled, upon sleeping players whose energy is less than 80% will gain a default of 0.25 total stamina. And players whose energy is greater than 92% will lose a default of 0.25 total stamina if they already have more than the default total. (Default: true)")
    public boolean fitnessSystem = true;
    @ConfigEntry(comment = "Amount of stamina players gain or loose when the fitness system is enabled. (Default: 0.25)")
    public double fitnessStaminaChange = 0.25;
    @ConfigEntry(comment = "Maximum stamina players can reach with the fitness system. (Default: 0.25)")
    public double fitnessStaminaLimit = 128.0;
    @ConfigEntry(comment = "Enables or disables the energy system. (Default: true)")
    public boolean enableEnergySystem = true;

    public RStaminaConfig() {
        super(ConfigOptions.mod(RStaminaMod.modid));
    }

}
