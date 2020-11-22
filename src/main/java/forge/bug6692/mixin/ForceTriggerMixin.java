package forge.bug6692.mixin;

import net.minecraft.client.MainWindow;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainWindow.class)
public class ForceTriggerMixin {

    @Shadow private int framebufferWidth;
    @Shadow private int framebufferHeight;

    @Inject(method = "updateFramebufferSize", at = @At("RETURN"))
    public void forceTrigger(CallbackInfo ci) {
        if (!Boolean.parseBoolean(System.getProperty("forge.bug6692.force", "true"))) return;

        framebufferWidth = 0;
        framebufferHeight = 0;
    }

}
