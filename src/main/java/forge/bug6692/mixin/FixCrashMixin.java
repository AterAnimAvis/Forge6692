package forge.bug6692.mixin;

import net.minecraft.client.MainWindow;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MainWindow.class)
public class FixCrashMixin {

    @Redirect(method = "updateFramebufferSize", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwGetFramebufferSize(J[I[I)V"))
    public void fix(long handle, int[] width, int[] height) {
        if (!Boolean.parseBoolean(System.getProperty("forge.bug6692.fix", "true"))) {
            GLFW.glfwGetFramebufferSize(handle, width, height);
            return;
        }

        boolean isIconified = GLFW.glfwGetWindowAttrib(handle, GLFW.GLFW_ICONIFIED) == GLFW.GLFW_TRUE;
        if (isIconified) GLFW.glfwRestoreWindow(handle);
        GLFW.glfwGetFramebufferSize(handle, width, height);
        if (isIconified) GLFW.glfwIconifyWindow(handle);

    }

}
