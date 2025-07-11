package com.moulberry.flashback.keyframe.types;

import com.moulberry.flashback.keyframe.KeyframeType;
import com.moulberry.flashback.keyframe.change.KeyframeChange;
import com.moulberry.flashback.keyframe.change.KeyframeChangeGamma;
import com.moulberry.flashback.keyframe.handler.KeyframeHandler;
import com.moulberry.flashback.keyframe.impl.GammaKeyframe;
import com.moulberry.flashback.state.EditorState;
import com.moulberry.flashback.state.EditorStateManager;
import imgui.ImGui;
import imgui.type.ImFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;


public class GammaKeyframeType implements KeyframeType<GammaKeyframe> {

    public static GammaKeyframeType INSTANCE = new GammaKeyframeType();

    private GammaKeyframeType() {
    }

    @Override
    public Class<? extends KeyframeChange> keyframeChangeType() {
        return KeyframeChangeGamma.class;
    }

    @Override
    public @Nullable String icon() {
        return "\ue0f0";
    }

    @Override
    public String name() {
        return I18n.get("flashback.keyframe.gamma");
    }

    @Override
    public String id() {
        return "Gamma";
    }

    @Override
    public @Nullable GammaKeyframe createDirect() {
        return null;
    }

    @Override
    public KeyframeCreatePopup<GammaKeyframe> createPopup() {
        float[] gammaKeyframeInput = new float[]{
            Minecraft.getInstance().options.gamma().get().floatValue()};
        EditorState editorState = EditorStateManager.getCurrent();
        if (editorState != null && editorState.replayVisuals.overrideGamma) {
            gammaKeyframeInput[0] = editorState.replayVisuals.overrideGammaAmount;
        }

        return () -> {
            ImGui.sliderFloat(I18n.get("flashback.gamma"), gammaKeyframeInput, 0.0f,
                              1.0f);
            if (ImGui.button(I18n.get("flashback.add"))) {
                return new GammaKeyframe(gammaKeyframeInput[0]);
            }
            ImGui.sameLine();
            if (ImGui.button(I18n.get("gui.cancel"))) {
                ImGui.closeCurrentPopup();
            }
            return null;
        };
    }
}
