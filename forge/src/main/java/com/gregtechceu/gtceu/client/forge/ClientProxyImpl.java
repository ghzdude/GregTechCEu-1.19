package com.gregtechceu.gtceu.client.forge;

import com.gregtechceu.gtceu.forge.CommonProxyImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class ClientProxyImpl extends CommonProxyImpl {

    public ClientProxyImpl() {
        super();
    }

}
