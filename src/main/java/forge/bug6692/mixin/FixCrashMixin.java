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
public class FixCrashMixin {

    @Shadow private int framebufferWidth;
    @Shadow private int width;
    @Shadow private int framebufferHeight;
    @Shadow private int height;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void autoMinimize(CallbackInfo ci) {
        if (!Boolean.parseBoolean(System.getProperty("forge.bug6692.fix", "false"))) return;

        if (framebufferWidth == 0) framebufferWidth = width;
        if (framebufferHeight == 0) framebufferHeight = height;
    }

}
