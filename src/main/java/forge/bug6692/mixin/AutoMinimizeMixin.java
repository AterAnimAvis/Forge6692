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
public class AutoMinimizeMixin {

    @Shadow @Final private long handle;

    @Inject(method = "updateFramebufferSize", at = @At("HEAD"))
    public void autoMinimize(CallbackInfo ci) {
        if (!Boolean.parseBoolean(System.getProperty("forge.bug6692.minimize", "true"))) return;

        boolean isIconified = GLFW.glfwGetWindowAttrib(handle, GLFW.GLFW_ICONIFIED) == GLFW.GLFW_TRUE;
        if (isIconified) return;

        GLFW.glfwIconifyWindow(handle);
        GLFW.glfwPollEvents();
    }

}
