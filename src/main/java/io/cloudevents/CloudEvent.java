/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.cloudevents;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

/** Avro Event Format for CloudEvents. Branching from cloudevents.io. 4.2.2024 */
@org.apache.avro.specific.AvroGenerated
public class CloudEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 6951573015317024475L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CloudEvent\",\"namespace\":\"io.cloudevents\",\"doc\":\"Avro Event Format for CloudEvents. Branching from cloudevents.io. 4.2.2024\",\"fields\":[{\"name\":\"specversion\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"id\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"source\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"type\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"datacontenttype\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"dataschema\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"subject\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"time\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"data\",\"type\":[\"bytes\",\"null\",\"boolean\",{\"type\":\"map\",\"values\":[\"null\",\"boolean\",{\"type\":\"record\",\"name\":\"AvroCloudEventData\",\"doc\":\"Representation of a JSON Value\",\"fields\":[{\"name\":\"value\",\"type\":{\"type\":\"map\",\"values\":[\"null\",\"boolean\",{\"type\":\"map\",\"values\":\"AvroCloudEventData\"},{\"type\":\"array\",\"items\":\"AvroCloudEventData\"},\"double\",\"string\"]}}]},\"double\",\"string\"]},{\"type\":\"array\",\"items\":\"AvroCloudEventData\"},\"double\",\"string\"]}],\"version\":\"1.0\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CloudEvent> ENCODER =
      new BinaryMessageEncoder<CloudEvent>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CloudEvent> DECODER =
      new BinaryMessageDecoder<CloudEvent>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CloudEvent> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CloudEvent> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CloudEvent> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CloudEvent>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CloudEvent to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CloudEvent from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CloudEvent instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CloudEvent fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.CharSequence specversion;
   private java.lang.CharSequence id;
   private java.lang.CharSequence source;
   private java.lang.CharSequence type;
   private java.lang.CharSequence datacontenttype;
   private java.lang.CharSequence dataschema;
   private java.lang.CharSequence subject;
   private java.lang.CharSequence time;
   private java.lang.Object data;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CloudEvent() {}

  /**
   * All-args constructor.
   * @param specversion The new value for specversion
   * @param id The new value for id
   * @param source The new value for source
   * @param type The new value for type
   * @param datacontenttype The new value for datacontenttype
   * @param dataschema The new value for dataschema
   * @param subject The new value for subject
   * @param time The new value for time
   * @param data The new value for data
   */
  public CloudEvent(java.lang.CharSequence specversion, java.lang.CharSequence id, java.lang.CharSequence source, java.lang.CharSequence type, java.lang.CharSequence datacontenttype, java.lang.CharSequence dataschema, java.lang.CharSequence subject, java.lang.CharSequence time, java.lang.Object data) {
    this.specversion = specversion;
    this.id = id;
    this.source = source;
    this.type = type;
    this.datacontenttype = datacontenttype;
    this.dataschema = dataschema;
    this.subject = subject;
    this.time = time;
    this.data = data;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return specversion;
    case 1: return id;
    case 2: return source;
    case 3: return type;
    case 4: return datacontenttype;
    case 5: return dataschema;
    case 6: return subject;
    case 7: return time;
    case 8: return data;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: specversion = (java.lang.CharSequence)value$; break;
    case 1: id = (java.lang.CharSequence)value$; break;
    case 2: source = (java.lang.CharSequence)value$; break;
    case 3: type = (java.lang.CharSequence)value$; break;
    case 4: datacontenttype = (java.lang.CharSequence)value$; break;
    case 5: dataschema = (java.lang.CharSequence)value$; break;
    case 6: subject = (java.lang.CharSequence)value$; break;
    case 7: time = (java.lang.CharSequence)value$; break;
    case 8: data = value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'specversion' field.
   * @return The value of the 'specversion' field.
   */
  public java.lang.CharSequence getSpecversion() {
    return specversion;
  }


  /**
   * Sets the value of the 'specversion' field.
   * @param value the value to set.
   */
  public void setSpecversion(java.lang.CharSequence value) {
    this.specversion = value;
  }

  /**
   * Gets the value of the 'id' field.
   * @return The value of the 'id' field.
   */
  public java.lang.CharSequence getId() {
    return id;
  }


  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.lang.CharSequence value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'source' field.
   * @return The value of the 'source' field.
   */
  public java.lang.CharSequence getSource() {
    return source;
  }


  /**
   * Sets the value of the 'source' field.
   * @param value the value to set.
   */
  public void setSource(java.lang.CharSequence value) {
    this.source = value;
  }

  /**
   * Gets the value of the 'type' field.
   * @return The value of the 'type' field.
   */
  public java.lang.CharSequence getType() {
    return type;
  }


  /**
   * Sets the value of the 'type' field.
   * @param value the value to set.
   */
  public void setType(java.lang.CharSequence value) {
    this.type = value;
  }

  /**
   * Gets the value of the 'datacontenttype' field.
   * @return The value of the 'datacontenttype' field.
   */
  public java.lang.CharSequence getDatacontenttype() {
    return datacontenttype;
  }


  /**
   * Sets the value of the 'datacontenttype' field.
   * @param value the value to set.
   */
  public void setDatacontenttype(java.lang.CharSequence value) {
    this.datacontenttype = value;
  }

  /**
   * Gets the value of the 'dataschema' field.
   * @return The value of the 'dataschema' field.
   */
  public java.lang.CharSequence getDataschema() {
    return dataschema;
  }


  /**
   * Sets the value of the 'dataschema' field.
   * @param value the value to set.
   */
  public void setDataschema(java.lang.CharSequence value) {
    this.dataschema = value;
  }

  /**
   * Gets the value of the 'subject' field.
   * @return The value of the 'subject' field.
   */
  public java.lang.CharSequence getSubject() {
    return subject;
  }


  /**
   * Sets the value of the 'subject' field.
   * @param value the value to set.
   */
  public void setSubject(java.lang.CharSequence value) {
    this.subject = value;
  }

  /**
   * Gets the value of the 'time' field.
   * @return The value of the 'time' field.
   */
  public java.lang.CharSequence getTime() {
    return time;
  }


  /**
   * Sets the value of the 'time' field.
   * @param value the value to set.
   */
  public void setTime(java.lang.CharSequence value) {
    this.time = value;
  }

  /**
   * Gets the value of the 'data' field.
   * @return The value of the 'data' field.
   */
  public java.lang.Object getData() {
    return data;
  }


  /**
   * Sets the value of the 'data' field.
   * @param value the value to set.
   */
  public void setData(java.lang.Object value) {
    this.data = value;
  }

  /**
   * Creates a new CloudEvent RecordBuilder.
   * @return A new CloudEvent RecordBuilder
   */
  public static CloudEvent.Builder newBuilder() {
    return new CloudEvent.Builder();
  }

  /**
   * Creates a new CloudEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CloudEvent RecordBuilder
   */
  public static CloudEvent.Builder newBuilder(CloudEvent.Builder other) {
    if (other == null) {
      return new CloudEvent.Builder();
    } else {
      return new CloudEvent.Builder(other);
    }
  }

  /**
   * Creates a new CloudEvent RecordBuilder by copying an existing CloudEvent instance.
   * @param other The existing instance to copy.
   * @return A new CloudEvent RecordBuilder
   */
  public static CloudEvent.Builder newBuilder(CloudEvent other) {
    if (other == null) {
      return new CloudEvent.Builder();
    } else {
      return new CloudEvent.Builder(other);
    }
  }

  /**
   * RecordBuilder for CloudEvent instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CloudEvent>
    implements org.apache.avro.data.RecordBuilder<CloudEvent> {

    private java.lang.CharSequence specversion;
    private java.lang.CharSequence id;
    private java.lang.CharSequence source;
    private java.lang.CharSequence type;
    private java.lang.CharSequence datacontenttype;
    private java.lang.CharSequence dataschema;
    private java.lang.CharSequence subject;
    private java.lang.CharSequence time;
    private java.lang.Object data;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(CloudEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.specversion)) {
        this.specversion = data().deepCopy(fields()[0].schema(), other.specversion);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.id)) {
        this.id = data().deepCopy(fields()[1].schema(), other.id);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.source)) {
        this.source = data().deepCopy(fields()[2].schema(), other.source);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.type)) {
        this.type = data().deepCopy(fields()[3].schema(), other.type);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.datacontenttype)) {
        this.datacontenttype = data().deepCopy(fields()[4].schema(), other.datacontenttype);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.dataschema)) {
        this.dataschema = data().deepCopy(fields()[5].schema(), other.dataschema);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.subject)) {
        this.subject = data().deepCopy(fields()[6].schema(), other.subject);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.time)) {
        this.time = data().deepCopy(fields()[7].schema(), other.time);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
      if (isValidValue(fields()[8], other.data)) {
        this.data = data().deepCopy(fields()[8].schema(), other.data);
        fieldSetFlags()[8] = other.fieldSetFlags()[8];
      }
    }

    /**
     * Creates a Builder by copying an existing CloudEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(CloudEvent other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.specversion)) {
        this.specversion = data().deepCopy(fields()[0].schema(), other.specversion);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.id)) {
        this.id = data().deepCopy(fields()[1].schema(), other.id);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.source)) {
        this.source = data().deepCopy(fields()[2].schema(), other.source);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.type)) {
        this.type = data().deepCopy(fields()[3].schema(), other.type);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.datacontenttype)) {
        this.datacontenttype = data().deepCopy(fields()[4].schema(), other.datacontenttype);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.dataschema)) {
        this.dataschema = data().deepCopy(fields()[5].schema(), other.dataschema);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.subject)) {
        this.subject = data().deepCopy(fields()[6].schema(), other.subject);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.time)) {
        this.time = data().deepCopy(fields()[7].schema(), other.time);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.data)) {
        this.data = data().deepCopy(fields()[8].schema(), other.data);
        fieldSetFlags()[8] = true;
      }
    }

    /**
      * Gets the value of the 'specversion' field.
      * @return The value.
      */
    public java.lang.CharSequence getSpecversion() {
      return specversion;
    }


    /**
      * Sets the value of the 'specversion' field.
      * @param value The value of 'specversion'.
      * @return This builder.
      */
    public CloudEvent.Builder setSpecversion(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.specversion = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'specversion' field has been set.
      * @return True if the 'specversion' field has been set, false otherwise.
      */
    public boolean hasSpecversion() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'specversion' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearSpecversion() {
      specversion = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'id' field.
      * @return The value.
      */
    public java.lang.CharSequence getId() {
      return id;
    }


    /**
      * Sets the value of the 'id' field.
      * @param value The value of 'id'.
      * @return This builder.
      */
    public CloudEvent.Builder setId(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.id = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'id' field has been set.
      * @return True if the 'id' field has been set, false otherwise.
      */
    public boolean hasId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'id' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearId() {
      id = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'source' field.
      * @return The value.
      */
    public java.lang.CharSequence getSource() {
      return source;
    }


    /**
      * Sets the value of the 'source' field.
      * @param value The value of 'source'.
      * @return This builder.
      */
    public CloudEvent.Builder setSource(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.source = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'source' field has been set.
      * @return True if the 'source' field has been set, false otherwise.
      */
    public boolean hasSource() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'source' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearSource() {
      source = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'type' field.
      * @return The value.
      */
    public java.lang.CharSequence getType() {
      return type;
    }


    /**
      * Sets the value of the 'type' field.
      * @param value The value of 'type'.
      * @return This builder.
      */
    public CloudEvent.Builder setType(java.lang.CharSequence value) {
      validate(fields()[3], value);
      this.type = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'type' field has been set.
      * @return True if the 'type' field has been set, false otherwise.
      */
    public boolean hasType() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'type' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearType() {
      type = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'datacontenttype' field.
      * @return The value.
      */
    public java.lang.CharSequence getDatacontenttype() {
      return datacontenttype;
    }


    /**
      * Sets the value of the 'datacontenttype' field.
      * @param value The value of 'datacontenttype'.
      * @return This builder.
      */
    public CloudEvent.Builder setDatacontenttype(java.lang.CharSequence value) {
      validate(fields()[4], value);
      this.datacontenttype = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'datacontenttype' field has been set.
      * @return True if the 'datacontenttype' field has been set, false otherwise.
      */
    public boolean hasDatacontenttype() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'datacontenttype' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearDatacontenttype() {
      datacontenttype = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'dataschema' field.
      * @return The value.
      */
    public java.lang.CharSequence getDataschema() {
      return dataschema;
    }


    /**
      * Sets the value of the 'dataschema' field.
      * @param value The value of 'dataschema'.
      * @return This builder.
      */
    public CloudEvent.Builder setDataschema(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.dataschema = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'dataschema' field has been set.
      * @return True if the 'dataschema' field has been set, false otherwise.
      */
    public boolean hasDataschema() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'dataschema' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearDataschema() {
      dataschema = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'subject' field.
      * @return The value.
      */
    public java.lang.CharSequence getSubject() {
      return subject;
    }


    /**
      * Sets the value of the 'subject' field.
      * @param value The value of 'subject'.
      * @return This builder.
      */
    public CloudEvent.Builder setSubject(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.subject = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'subject' field has been set.
      * @return True if the 'subject' field has been set, false otherwise.
      */
    public boolean hasSubject() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'subject' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearSubject() {
      subject = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'time' field.
      * @return The value.
      */
    public java.lang.CharSequence getTime() {
      return time;
    }


    /**
      * Sets the value of the 'time' field.
      * @param value The value of 'time'.
      * @return This builder.
      */
    public CloudEvent.Builder setTime(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.time = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'time' field has been set.
      * @return True if the 'time' field has been set, false otherwise.
      */
    public boolean hasTime() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'time' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearTime() {
      time = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /**
      * Gets the value of the 'data' field.
      * @return The value.
      */
    public java.lang.Object getData() {
      return data;
    }


    /**
      * Sets the value of the 'data' field.
      * @param value The value of 'data'.
      * @return This builder.
      */
    public CloudEvent.Builder setData(java.lang.Object value) {
      validate(fields()[8], value);
      this.data = value;
      fieldSetFlags()[8] = true;
      return this;
    }

    /**
      * Checks whether the 'data' field has been set.
      * @return True if the 'data' field has been set, false otherwise.
      */
    public boolean hasData() {
      return fieldSetFlags()[8];
    }


    /**
      * Clears the value of the 'data' field.
      * @return This builder.
      */
    public CloudEvent.Builder clearData() {
      data = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CloudEvent build() {
      try {
        CloudEvent record = new CloudEvent();
        record.specversion = fieldSetFlags()[0] ? this.specversion : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.id = fieldSetFlags()[1] ? this.id : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.source = fieldSetFlags()[2] ? this.source : (java.lang.CharSequence) defaultValue(fields()[2]);
        record.type = fieldSetFlags()[3] ? this.type : (java.lang.CharSequence) defaultValue(fields()[3]);
        record.datacontenttype = fieldSetFlags()[4] ? this.datacontenttype : (java.lang.CharSequence) defaultValue(fields()[4]);
        record.dataschema = fieldSetFlags()[5] ? this.dataschema : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.subject = fieldSetFlags()[6] ? this.subject : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.time = fieldSetFlags()[7] ? this.time : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.data = fieldSetFlags()[8] ? this.data :  defaultValue(fields()[8]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CloudEvent>
    WRITER$ = (org.apache.avro.io.DatumWriter<CloudEvent>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CloudEvent>
    READER$ = (org.apache.avro.io.DatumReader<CloudEvent>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










