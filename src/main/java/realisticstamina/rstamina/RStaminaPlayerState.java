package realisticstamina.rstamina;

public class RStaminaPlayerState {
    public int testplayerdata = 200;

    public double stamina = RStaminaMod.config.totalStamina;
    public double maxStamina = RStaminaMod.config.totalStamina;
    public double totalStamina = RStaminaMod.config.totalStamina;
    public double energy = 100.0;
    public double energyFromResting = 0.0;
    public boolean edited = false;

    public int staminaRegenCooldown = 0;

    //rates
    public double staminaLossRate = 0.25;
    public double staminaGainRate = 0.125;

    public double energyLossRate = 0.004;
    public double energyGainRate = RStaminaMod.config.restingEnergyGainTick;

}
