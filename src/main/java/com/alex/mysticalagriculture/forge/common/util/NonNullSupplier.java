package com.alex.mysticalagriculture.forge.common.util;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface NonNullSupplier<T>
{
    @NotNull T get();
}
