package com.github.yannicklamprecht.modernchat.modernchat.config;

public record ModernChatConfig(
        String chatformat
) {
    public ModernChatConfig() {
        this("<gray>[<job>|<group>] <name> >> <message>");
    }
}
