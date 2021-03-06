// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: compiler/ir/serialization.common/src/KotlinIr.proto

package org.jetbrains.kotlin.backend.common.serialization.proto;

/**
 * Protobuf type {@code org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments}
 */
public final class TypeArguments extends
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite implements
    // @@protoc_insertion_point(message_implements:org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments)
    TypeArgumentsOrBuilder {
  // Use TypeArguments.newBuilder() to construct.
  private TypeArguments(org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder builder) {
    super(builder);
    this.unknownFields = builder.getUnknownFields();
  }
  private TypeArguments(boolean noInit) { this.unknownFields = org.jetbrains.kotlin.protobuf.ByteString.EMPTY;}

  private static final TypeArguments defaultInstance;
  public static TypeArguments getDefaultInstance() {
    return defaultInstance;
  }

  public TypeArguments getDefaultInstanceForType() {
    return defaultInstance;
  }

  private final org.jetbrains.kotlin.protobuf.ByteString unknownFields;
  private TypeArguments(
      org.jetbrains.kotlin.protobuf.CodedInputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    initFields();
    int mutable_bitField0_ = 0;
    org.jetbrains.kotlin.protobuf.ByteString.Output unknownFieldsOutput =
        org.jetbrains.kotlin.protobuf.ByteString.newOutput();
    org.jetbrains.kotlin.protobuf.CodedOutputStream unknownFieldsCodedOutput =
        org.jetbrains.kotlin.protobuf.CodedOutputStream.newInstance(
            unknownFieldsOutput);
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          default: {
            if (!parseUnknownField(input, unknownFieldsCodedOutput,
                                   extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
              typeArgument_ = new java.util.ArrayList<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex>();
              mutable_bitField0_ |= 0x00000001;
            }
            typeArgument_.add(input.readMessage(org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex.PARSER, extensionRegistry));
            break;
          }
        }
      }
    } catch (org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException(
          e.getMessage()).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
        typeArgument_ = java.util.Collections.unmodifiableList(typeArgument_);
      }
      try {
        unknownFieldsCodedOutput.flush();
      } catch (java.io.IOException e) {
      // Should not happen
      } finally {
        unknownFields = unknownFieldsOutput.toByteString();
      }
      makeExtensionsImmutable();
    }
  }
  public static org.jetbrains.kotlin.protobuf.Parser<TypeArguments> PARSER =
      new org.jetbrains.kotlin.protobuf.AbstractParser<TypeArguments>() {
    public TypeArguments parsePartialFrom(
        org.jetbrains.kotlin.protobuf.CodedInputStream input,
        org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
        throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
      return new TypeArguments(input, extensionRegistry);
    }
  };

  @java.lang.Override
  public org.jetbrains.kotlin.protobuf.Parser<TypeArguments> getParserForType() {
    return PARSER;
  }

  public static final int TYPE_ARGUMENT_FIELD_NUMBER = 1;
  private java.util.List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex> typeArgument_;
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
   */
  public java.util.List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex> getTypeArgumentList() {
    return typeArgument_;
  }
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
   */
  public java.util.List<? extends org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndexOrBuilder> 
      getTypeArgumentOrBuilderList() {
    return typeArgument_;
  }
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
   */
  public int getTypeArgumentCount() {
    return typeArgument_.size();
  }
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
   */
  public org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex getTypeArgument(int index) {
    return typeArgument_.get(index);
  }
  /**
   * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
   */
  public org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndexOrBuilder getTypeArgumentOrBuilder(
      int index) {
    return typeArgument_.get(index);
  }

  private void initFields() {
    typeArgument_ = java.util.Collections.emptyList();
  }
  private byte memoizedIsInitialized = -1;
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    for (int i = 0; i < getTypeArgumentCount(); i++) {
      if (!getTypeArgument(i).isInitialized()) {
        memoizedIsInitialized = 0;
        return false;
      }
    }
    memoizedIsInitialized = 1;
    return true;
  }

  public void writeTo(org.jetbrains.kotlin.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    getSerializedSize();
    for (int i = 0; i < typeArgument_.size(); i++) {
      output.writeMessage(1, typeArgument_.get(i));
    }
    output.writeRawBytes(unknownFields);
  }

  private int memoizedSerializedSize = -1;
  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < typeArgument_.size(); i++) {
      size += org.jetbrains.kotlin.protobuf.CodedOutputStream
        .computeMessageSize(1, typeArgument_.get(i));
    }
    size += unknownFields.size();
    memoizedSerializedSize = size;
    return size;
  }

  private static final long serialVersionUID = 0L;
  @java.lang.Override
  protected java.lang.Object writeReplace()
      throws java.io.ObjectStreamException {
    return super.writeReplace();
  }

  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(
      org.jetbrains.kotlin.protobuf.ByteString data)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(
      org.jetbrains.kotlin.protobuf.ByteString data,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(byte[] data)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(
      byte[] data,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(
      java.io.InputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseDelimitedFrom(
      java.io.InputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseDelimitedFrom(input, extensionRegistry);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(
      org.jetbrains.kotlin.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return PARSER.parseFrom(input);
  }
  public static org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parseFrom(
      org.jetbrains.kotlin.protobuf.CodedInputStream input,
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return PARSER.parseFrom(input, extensionRegistry);
  }

  public static Builder newBuilder() { return Builder.create(); }
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder(org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments prototype) {
    return newBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() { return newBuilder(this); }

  /**
   * Protobuf type {@code org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments}
   */
  public static final class Builder extends
      org.jetbrains.kotlin.protobuf.GeneratedMessageLite.Builder<
        org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments, Builder>
      implements
      // @@protoc_insertion_point(builder_implements:org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments)
      org.jetbrains.kotlin.backend.common.serialization.proto.TypeArgumentsOrBuilder {
    // Construct using org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private void maybeForceBuilderInitialization() {
    }
    private static Builder create() {
      return new Builder();
    }

    public Builder clear() {
      super.clear();
      typeArgument_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);
      return this;
    }

    public Builder clone() {
      return create().mergeFrom(buildPartial());
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments getDefaultInstanceForType() {
      return org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments.getDefaultInstance();
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments build() {
      org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    public org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments buildPartial() {
      org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments result = new org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments(this);
      int from_bitField0_ = bitField0_;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        typeArgument_ = java.util.Collections.unmodifiableList(typeArgument_);
        bitField0_ = (bitField0_ & ~0x00000001);
      }
      result.typeArgument_ = typeArgument_;
      return result;
    }

    public Builder mergeFrom(org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments other) {
      if (other == org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments.getDefaultInstance()) return this;
      if (!other.typeArgument_.isEmpty()) {
        if (typeArgument_.isEmpty()) {
          typeArgument_ = other.typeArgument_;
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          ensureTypeArgumentIsMutable();
          typeArgument_.addAll(other.typeArgument_);
        }
        
      }
      setUnknownFields(
          getUnknownFields().concat(other.unknownFields));
      return this;
    }

    public final boolean isInitialized() {
      for (int i = 0; i < getTypeArgumentCount(); i++) {
        if (!getTypeArgument(i).isInitialized()) {
          
          return false;
        }
      }
      return true;
    }

    public Builder mergeFrom(
        org.jetbrains.kotlin.protobuf.CodedInputStream input,
        org.jetbrains.kotlin.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (org.jetbrains.kotlin.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments) e.getUnfinishedMessage();
        throw e;
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex> typeArgument_ =
      java.util.Collections.emptyList();
    private void ensureTypeArgumentIsMutable() {
      if (!((bitField0_ & 0x00000001) == 0x00000001)) {
        typeArgument_ = new java.util.ArrayList<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex>(typeArgument_);
        bitField0_ |= 0x00000001;
       }
    }

    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public java.util.List<org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex> getTypeArgumentList() {
      return java.util.Collections.unmodifiableList(typeArgument_);
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public int getTypeArgumentCount() {
      return typeArgument_.size();
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex getTypeArgument(int index) {
      return typeArgument_.get(index);
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder setTypeArgument(
        int index, org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureTypeArgumentIsMutable();
      typeArgument_.set(index, value);

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder setTypeArgument(
        int index, org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex.Builder builderForValue) {
      ensureTypeArgumentIsMutable();
      typeArgument_.set(index, builderForValue.build());

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder addTypeArgument(org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureTypeArgumentIsMutable();
      typeArgument_.add(value);

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder addTypeArgument(
        int index, org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex value) {
      if (value == null) {
        throw new NullPointerException();
      }
      ensureTypeArgumentIsMutable();
      typeArgument_.add(index, value);

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder addTypeArgument(
        org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex.Builder builderForValue) {
      ensureTypeArgumentIsMutable();
      typeArgument_.add(builderForValue.build());

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder addTypeArgument(
        int index, org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex.Builder builderForValue) {
      ensureTypeArgumentIsMutable();
      typeArgument_.add(index, builderForValue.build());

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder addAllTypeArgument(
        java.lang.Iterable<? extends org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex> values) {
      ensureTypeArgumentIsMutable();
      org.jetbrains.kotlin.protobuf.AbstractMessageLite.Builder.addAll(
          values, typeArgument_);

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder clearTypeArgument() {
      typeArgument_ = java.util.Collections.emptyList();
      bitField0_ = (bitField0_ & ~0x00000001);

      return this;
    }
    /**
     * <code>repeated .org.jetbrains.kotlin.backend.common.serialization.proto.IrTypeIndex type_argument = 1;</code>
     */
    public Builder removeTypeArgument(int index) {
      ensureTypeArgumentIsMutable();
      typeArgument_.remove(index);

      return this;
    }

    // @@protoc_insertion_point(builder_scope:org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments)
  }

  static {
    defaultInstance = new TypeArguments(true);
    defaultInstance.initFields();
  }

  // @@protoc_insertion_point(class_scope:org.jetbrains.kotlin.backend.common.serialization.proto.TypeArguments)
}
