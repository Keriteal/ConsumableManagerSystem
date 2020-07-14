// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ConfirmProto.proto

package model.protobuf;

public final class ConfirmProto {
  private ConfirmProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }
  public interface ConfirmRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ConfirmRequest)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>string uuid = 1;</code>
     * @return The uuid.
     */
    java.lang.String getUuid();
    /**
     * <code>string uuid = 1;</code>
     * @return The bytes for uuid.
     */
    com.google.protobuf.ByteString
        getUuidBytes();

    /**
     * <code>string secret = 2;</code>
     * @return The secret.
     */
    java.lang.String getSecret();
    /**
     * <code>string secret = 2;</code>
     * @return The bytes for secret.
     */
    com.google.protobuf.ByteString
        getSecretBytes();

    /**
     * <code>uint32 recordId = 3;</code>
     * @return The recordId.
     */
    int getRecordId();
  }
  /**
   * Protobuf type {@code ConfirmRequest}
   */
  public  static final class ConfirmRequest extends
      com.google.protobuf.GeneratedMessageLite<
          ConfirmRequest, ConfirmRequest.Builder> implements
      // @@protoc_insertion_point(message_implements:ConfirmRequest)
      ConfirmRequestOrBuilder {
    private ConfirmRequest() {
      uuid_ = "";
      secret_ = "";
    }
    public static final int UUID_FIELD_NUMBER = 1;
    private java.lang.String uuid_;
    /**
     * <code>string uuid = 1;</code>
     * @return The uuid.
     */
    @java.lang.Override
    public java.lang.String getUuid() {
      return uuid_;
    }
    /**
     * <code>string uuid = 1;</code>
     * @return The bytes for uuid.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getUuidBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(uuid_);
    }
    /**
     * <code>string uuid = 1;</code>
     * @param value The uuid to set.
     */
    private void setUuid(
        java.lang.String value) {
      value.getClass();
  
      uuid_ = value;
    }
    /**
     * <code>string uuid = 1;</code>
     */
    private void clearUuid() {
      
      uuid_ = getDefaultInstance().getUuid();
    }
    /**
     * <code>string uuid = 1;</code>
     * @param value The bytes for uuid to set.
     */
    private void setUuidBytes(
        com.google.protobuf.ByteString value) {
      checkByteStringIsUtf8(value);
      uuid_ = value.toStringUtf8();
      
    }

    public static final int SECRET_FIELD_NUMBER = 2;
    private java.lang.String secret_;
    /**
     * <code>string secret = 2;</code>
     * @return The secret.
     */
    @java.lang.Override
    public java.lang.String getSecret() {
      return secret_;
    }
    /**
     * <code>string secret = 2;</code>
     * @return The bytes for secret.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getSecretBytes() {
      return com.google.protobuf.ByteString.copyFromUtf8(secret_);
    }
    /**
     * <code>string secret = 2;</code>
     * @param value The secret to set.
     */
    private void setSecret(
        java.lang.String value) {
      value.getClass();
  
      secret_ = value;
    }
    /**
     * <code>string secret = 2;</code>
     */
    private void clearSecret() {
      
      secret_ = getDefaultInstance().getSecret();
    }
    /**
     * <code>string secret = 2;</code>
     * @param value The bytes for secret to set.
     */
    private void setSecretBytes(
        com.google.protobuf.ByteString value) {
      checkByteStringIsUtf8(value);
      secret_ = value.toStringUtf8();
      
    }

    public static final int RECORDID_FIELD_NUMBER = 3;
    private int recordId_;
    /**
     * <code>uint32 recordId = 3;</code>
     * @return The recordId.
     */
    @java.lang.Override
    public int getRecordId() {
      return recordId_;
    }
    /**
     * <code>uint32 recordId = 3;</code>
     * @param value The recordId to set.
     */
    private void setRecordId(int value) {
      
      recordId_ = value;
    }
    /**
     * <code>uint32 recordId = 3;</code>
     */
    private void clearRecordId() {
      
      recordId_ = 0;
    }

    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static model.protobuf.ConfirmProto.ConfirmRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(model.protobuf.ConfirmProto.ConfirmRequest prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code ConfirmRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          model.protobuf.ConfirmProto.ConfirmRequest, Builder> implements
        // @@protoc_insertion_point(builder_implements:ConfirmRequest)
        model.protobuf.ConfirmProto.ConfirmRequestOrBuilder {
      // Construct using model.protobuf.ConfirmProto.ConfirmRequest.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>string uuid = 1;</code>
       * @return The uuid.
       */
      @java.lang.Override
      public java.lang.String getUuid() {
        return instance.getUuid();
      }
      /**
       * <code>string uuid = 1;</code>
       * @return The bytes for uuid.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getUuidBytes() {
        return instance.getUuidBytes();
      }
      /**
       * <code>string uuid = 1;</code>
       * @param value The uuid to set.
       * @return This builder for chaining.
       */
      public Builder setUuid(
          java.lang.String value) {
        copyOnWrite();
        instance.setUuid(value);
        return this;
      }
      /**
       * <code>string uuid = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearUuid() {
        copyOnWrite();
        instance.clearUuid();
        return this;
      }
      /**
       * <code>string uuid = 1;</code>
       * @param value The bytes for uuid to set.
       * @return This builder for chaining.
       */
      public Builder setUuidBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setUuidBytes(value);
        return this;
      }

      /**
       * <code>string secret = 2;</code>
       * @return The secret.
       */
      @java.lang.Override
      public java.lang.String getSecret() {
        return instance.getSecret();
      }
      /**
       * <code>string secret = 2;</code>
       * @return The bytes for secret.
       */
      @java.lang.Override
      public com.google.protobuf.ByteString
          getSecretBytes() {
        return instance.getSecretBytes();
      }
      /**
       * <code>string secret = 2;</code>
       * @param value The secret to set.
       * @return This builder for chaining.
       */
      public Builder setSecret(
          java.lang.String value) {
        copyOnWrite();
        instance.setSecret(value);
        return this;
      }
      /**
       * <code>string secret = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearSecret() {
        copyOnWrite();
        instance.clearSecret();
        return this;
      }
      /**
       * <code>string secret = 2;</code>
       * @param value The bytes for secret to set.
       * @return This builder for chaining.
       */
      public Builder setSecretBytes(
          com.google.protobuf.ByteString value) {
        copyOnWrite();
        instance.setSecretBytes(value);
        return this;
      }

      /**
       * <code>uint32 recordId = 3;</code>
       * @return The recordId.
       */
      @java.lang.Override
      public int getRecordId() {
        return instance.getRecordId();
      }
      /**
       * <code>uint32 recordId = 3;</code>
       * @param value The recordId to set.
       * @return This builder for chaining.
       */
      public Builder setRecordId(int value) {
        copyOnWrite();
        instance.setRecordId(value);
        return this;
      }
      /**
       * <code>uint32 recordId = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearRecordId() {
        copyOnWrite();
        instance.clearRecordId();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:ConfirmRequest)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new model.protobuf.ConfirmProto.ConfirmRequest();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "uuid_",
              "secret_",
              "recordId_",
            };
            java.lang.String info =
                "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0208\u0002\u0208" +
                "\u0003\u000b";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<model.protobuf.ConfirmProto.ConfirmRequest> parser = PARSER;
          if (parser == null) {
            synchronized (model.protobuf.ConfirmProto.ConfirmRequest.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<model.protobuf.ConfirmProto.ConfirmRequest>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:ConfirmRequest)
    private static final model.protobuf.ConfirmProto.ConfirmRequest DEFAULT_INSTANCE;
    static {
      ConfirmRequest defaultInstance = new ConfirmRequest();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        ConfirmRequest.class, defaultInstance);
    }

    public static model.protobuf.ConfirmProto.ConfirmRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<ConfirmRequest> PARSER;

    public static com.google.protobuf.Parser<ConfirmRequest> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }

  public interface ConfirmResponseOrBuilder extends
      // @@protoc_insertion_point(interface_extends:ConfirmResponse)
      com.google.protobuf.MessageLiteOrBuilder {

    /**
     * <code>.ConfirmResponse.Result result = 1;</code>
     * @return The enum numeric value on the wire for result.
     */
    int getResultValue();
    /**
     * <code>.ConfirmResponse.Result result = 1;</code>
     * @return The result.
     */
    model.protobuf.ConfirmProto.ConfirmResponse.Result getResult();
  }
  /**
   * Protobuf type {@code ConfirmResponse}
   */
  public  static final class ConfirmResponse extends
      com.google.protobuf.GeneratedMessageLite<
          ConfirmResponse, ConfirmResponse.Builder> implements
      // @@protoc_insertion_point(message_implements:ConfirmResponse)
      ConfirmResponseOrBuilder {
    private ConfirmResponse() {
    }
    /**
     * Protobuf enum {@code ConfirmResponse.Result}
     */
    public enum Result
        implements com.google.protobuf.Internal.EnumLite {
      /**
       * <code>SUCCESS = 0;</code>
       */
      SUCCESS(0),
      /**
       * <code>FAILED = 1;</code>
       */
      FAILED(1),
      UNRECOGNIZED(-1),
      ;

      /**
       * <code>SUCCESS = 0;</code>
       */
      public static final int SUCCESS_VALUE = 0;
      /**
       * <code>FAILED = 1;</code>
       */
      public static final int FAILED_VALUE = 1;


      @java.lang.Override
      public final int getNumber() {
        if (this == UNRECOGNIZED) {
          throw new java.lang.IllegalArgumentException(
              "Can't get the number of an unknown enum value.");
        }
        return value;
      }

      /**
       * @param value The number of the enum to look for.
       * @return The enum associated with the given number.
       * @deprecated Use {@link #forNumber(int)} instead.
       */
      @java.lang.Deprecated
      public static Result valueOf(int value) {
        return forNumber(value);
      }

      public static Result forNumber(int value) {
        switch (value) {
          case 0: return SUCCESS;
          case 1: return FAILED;
          default: return null;
        }
      }

      public static com.google.protobuf.Internal.EnumLiteMap<Result>
          internalGetValueMap() {
        return internalValueMap;
      }
      private static final com.google.protobuf.Internal.EnumLiteMap<
          Result> internalValueMap =
            new com.google.protobuf.Internal.EnumLiteMap<Result>() {
              @java.lang.Override
              public Result findValueByNumber(int number) {
                return Result.forNumber(number);
              }
            };

      public static com.google.protobuf.Internal.EnumVerifier 
          internalGetVerifier() {
        return ResultVerifier.INSTANCE;
      }

      private static final class ResultVerifier implements 
           com.google.protobuf.Internal.EnumVerifier { 
              static final com.google.protobuf.Internal.EnumVerifier           INSTANCE = new ResultVerifier();
              @java.lang.Override
              public boolean isInRange(int number) {
                return Result.forNumber(number) != null;
              }
            };

      private final int value;

      private Result(int value) {
        this.value = value;
      }

      // @@protoc_insertion_point(enum_scope:ConfirmResponse.Result)
    }

    public static final int RESULT_FIELD_NUMBER = 1;
    private int result_;
    /**
     * <code>.ConfirmResponse.Result result = 1;</code>
     * @return The enum numeric value on the wire for result.
     */
    @java.lang.Override
    public int getResultValue() {
      return result_;
    }
    /**
     * <code>.ConfirmResponse.Result result = 1;</code>
     * @return The result.
     */
    @java.lang.Override
    public model.protobuf.ConfirmProto.ConfirmResponse.Result getResult() {
      model.protobuf.ConfirmProto.ConfirmResponse.Result result = model.protobuf.ConfirmProto.ConfirmResponse.Result.forNumber(result_);
      return result == null ? model.protobuf.ConfirmProto.ConfirmResponse.Result.UNRECOGNIZED : result;
    }
    /**
     * <code>.ConfirmResponse.Result result = 1;</code>
     * @param value The enum numeric value on the wire for result to set.
     */
    private void setResultValue(int value) {
        result_ = value;
    }
    /**
     * <code>.ConfirmResponse.Result result = 1;</code>
     * @param value The result to set.
     */
    private void setResult(model.protobuf.ConfirmProto.ConfirmResponse.Result value) {
      result_ = value.getNumber();
      
    }
    /**
     * <code>.ConfirmResponse.Result result = 1;</code>
     */
    private void clearResult() {
      
      result_ = 0;
    }

    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, data, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input);
    }
    public static model.protobuf.ConfirmProto.ConfirmResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageLite.parseFrom(
          DEFAULT_INSTANCE, input, extensionRegistry);
    }

    public static Builder newBuilder() {
      return (Builder) DEFAULT_INSTANCE.createBuilder();
    }
    public static Builder newBuilder(model.protobuf.ConfirmProto.ConfirmResponse prototype) {
      return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
    }

    /**
     * Protobuf type {@code ConfirmResponse}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageLite.Builder<
          model.protobuf.ConfirmProto.ConfirmResponse, Builder> implements
        // @@protoc_insertion_point(builder_implements:ConfirmResponse)
        model.protobuf.ConfirmProto.ConfirmResponseOrBuilder {
      // Construct using model.protobuf.ConfirmProto.ConfirmResponse.newBuilder()
      private Builder() {
        super(DEFAULT_INSTANCE);
      }


      /**
       * <code>.ConfirmResponse.Result result = 1;</code>
       * @return The enum numeric value on the wire for result.
       */
      @java.lang.Override
      public int getResultValue() {
        return instance.getResultValue();
      }
      /**
       * <code>.ConfirmResponse.Result result = 1;</code>
       * @param value The result to set.
       * @return This builder for chaining.
       */
      public Builder setResultValue(int value) {
        copyOnWrite();
        instance.setResultValue(value);
        return this;
      }
      /**
       * <code>.ConfirmResponse.Result result = 1;</code>
       * @return The result.
       */
      @java.lang.Override
      public model.protobuf.ConfirmProto.ConfirmResponse.Result getResult() {
        return instance.getResult();
      }
      /**
       * <code>.ConfirmResponse.Result result = 1;</code>
       * @param value The enum numeric value on the wire for result to set.
       * @return This builder for chaining.
       */
      public Builder setResult(model.protobuf.ConfirmProto.ConfirmResponse.Result value) {
        copyOnWrite();
        instance.setResult(value);
        return this;
      }
      /**
       * <code>.ConfirmResponse.Result result = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearResult() {
        copyOnWrite();
        instance.clearResult();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:ConfirmResponse)
    }
    @java.lang.Override
    @java.lang.SuppressWarnings({"unchecked", "fallthrough"})
    protected final java.lang.Object dynamicMethod(
        com.google.protobuf.GeneratedMessageLite.MethodToInvoke method,
        java.lang.Object arg0, java.lang.Object arg1) {
      switch (method) {
        case NEW_MUTABLE_INSTANCE: {
          return new model.protobuf.ConfirmProto.ConfirmResponse();
        }
        case NEW_BUILDER: {
          return new Builder();
        }
        case BUILD_MESSAGE_INFO: {
            java.lang.Object[] objects = new java.lang.Object[] {
              "result_",
            };
            java.lang.String info =
                "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001\f";
            return newMessageInfo(DEFAULT_INSTANCE, info, objects);
        }
        // fall through
        case GET_DEFAULT_INSTANCE: {
          return DEFAULT_INSTANCE;
        }
        case GET_PARSER: {
          com.google.protobuf.Parser<model.protobuf.ConfirmProto.ConfirmResponse> parser = PARSER;
          if (parser == null) {
            synchronized (model.protobuf.ConfirmProto.ConfirmResponse.class) {
              parser = PARSER;
              if (parser == null) {
                parser =
                    new DefaultInstanceBasedParser<model.protobuf.ConfirmProto.ConfirmResponse>(
                        DEFAULT_INSTANCE);
                PARSER = parser;
              }
            }
          }
          return parser;
      }
      case GET_MEMOIZED_IS_INITIALIZED: {
        return (byte) 1;
      }
      case SET_MEMOIZED_IS_INITIALIZED: {
        return null;
      }
      }
      throw new UnsupportedOperationException();
    }


    // @@protoc_insertion_point(class_scope:ConfirmResponse)
    private static final model.protobuf.ConfirmProto.ConfirmResponse DEFAULT_INSTANCE;
    static {
      ConfirmResponse defaultInstance = new ConfirmResponse();
      // New instances are implicitly immutable so no need to make
      // immutable.
      DEFAULT_INSTANCE = defaultInstance;
      com.google.protobuf.GeneratedMessageLite.registerDefaultInstance(
        ConfirmResponse.class, defaultInstance);
    }

    public static model.protobuf.ConfirmProto.ConfirmResponse getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static volatile com.google.protobuf.Parser<ConfirmResponse> PARSER;

    public static com.google.protobuf.Parser<ConfirmResponse> parser() {
      return DEFAULT_INSTANCE.getParserForType();
    }
  }


  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}