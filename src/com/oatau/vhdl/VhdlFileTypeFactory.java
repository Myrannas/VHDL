package com.oatau.vhdl;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: SESA185611
 * Date: 8/02/13
 * Time: 3:25 PM
 */
public class VhdlFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer consumer) {
        consumer.consume(VhdlFileType.INSTANCE,"vhdl");
    }
}
