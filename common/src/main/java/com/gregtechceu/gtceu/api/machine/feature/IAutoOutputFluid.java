package com.gregtechceu.gtceu.api.machine.feature;

import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

/**
 * @author KilaBash
 * @date 2023/3/2
 * @implNote IAutoOutputItem
 */
public interface IAutoOutputFluid {

    boolean isAutoOutputFluids();

    void setAutoOutputFluids(boolean allow);


    boolean isAllowInputFromOutputSideFluids();


    void setAllowInputFromOutputSideFluids(boolean allow);

    @Nullable
    Direction getOutputFacingFluids();


    void setOutputFacingFluids(Direction outputFacing);
}
