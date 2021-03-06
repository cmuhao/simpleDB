package simpledb;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 * author: Hao Xu
 */
public class TupleDesc {

	/**
	 * nested class to store the tuple descriptors
	 *
	 */
	private class Item {
		
		private Type itemType;
		private String itemName;
		
		public Item(Type inputType, String inputName) {
			setItemType(inputType);
			setItemName(inputName);
		}

		public Type getItemType() {
			return itemType;
		}

		public void setItemType(Type itemType) {
			this.itemType = itemType;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		
		
	}
	
	/**
	 * use array list to store the whole tuples
	 */
	private ArrayList<Item> list = new ArrayList<Item>();
	
    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields
     * fields, with the first td1.numFields coming from td1 and the remaining
     * from td2.
     * @param td1 The TupleDesc with the first fields of the new TupleDesc
     * @param td2 The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc combine(TupleDesc td1, TupleDesc td2) {
        // some code goes here
    	int len = td1.numFields() + td2.numFields();
    	Type[] typeAr = new Type[len];
    	String[] fieldAr = new String[len];
    	
    	for (int i = 0; i < td1.numFields(); i++) {
    		typeAr[i] = td1.getType(i);
    		fieldAr[i] = td1.getFieldName(i);
    	}
    	for (int i = 0; i < td2.numFields(); i++) {
    		typeAr[i + td1.numFields()] = td2.getType(i);
    		fieldAr[i + td1.numFields()] = td2.getFieldName(i);
    	}
    	return new TupleDesc(typeAr, fieldAr);
    }

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     *
     * @param typeAr array specifying the number of and types of fields in
     *        this TupleDesc. It must contain at least one entry.
     * @param fieldAr array specifying the names of the fields. Note that names may be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        // some code goes here
    	for (int i = 0; i < typeAr.length; i++) {
    		this.list.add(new Item(typeAr[i], fieldAr[i]));
    	}
    }

    /**
     * Constructor.
     * Create a new tuple desc with typeAr.length fields with fields of the
     * specified types, with anonymous (unnamed) fields.
     *
     * @param typeAr array specifying the number of and types of fields in
     *        this TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // some code goes here
    	for (int i = 0; i < typeAr.length; i++) {
    		this.list.add(new Item(typeAr[i], null));
    	}
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes here
        return this.list.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     *
     * @param i index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // some code goes here
        return this.list.get(i).getItemName();
    }

    /**
     * Find the index of the field with a given name.
     *
     * @param name name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException if no field with a matching name is found.
     */
    public int nameToId(String name) throws NoSuchElementException {
        // some code goes here
    	if (name == null) {
    		throw new NoSuchElementException();
    	}
    	for (int i = 0; i < this.numFields(); i++) {
    		if (this.getFieldName(i) != null && this.getFieldName(i).equals(name)) {
    			return i;
    		}
    	}
    	
        throw new NoSuchElementException();
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     *
     * @param i The index of the field to get the type of. It must be a valid index.
     * @return the type of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public Type getType(int i) throws NoSuchElementException {
        // some code goes here
    	if (i < 0 || i > this.numFields()) {
    		throw new NoSuchElementException();
    	}
        return this.list.get(i).getItemType();
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     * Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        // some code goes here
    	int size = 0;
        for (Item item : this.list) {
        	size += item.getItemType().getLen();
        }
        return size;
    }

    /**
     * Compares the specified object with this TupleDesc for equality.
     * Two TupleDescs are considered equal if they are the same size and if the
     * n-th type in this TupleDesc is equal to the n-th type in td.
     *
     * @param o the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */
    public boolean equals(Object o) {
        // some code goes here
        if (!(o instanceof TupleDesc)) {
        	return false;
        }
        TupleDesc tuple_o = (TupleDesc) o;
        if (tuple_o.getSize() != this.getSize()) {
        	return false;
        }
        for (int i = 0; i < tuple_o.numFields(); i++) {
        	if (!tuple_o.getType(i).equals(this.getType(i))) {
        		return false;
        	}
        }
        return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     * @return String describing this descriptor.
     */
    public String toString() {
        // some code goes here
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.numFields(); i++) {
        	s.append(this.getType(i));
        	s.append("(");
        	s.append(this.getFieldName(i));
        	s.append(")");
        	if (i != this.numFields() - 1) {
        		s.append(",");
        	}
        }
        return s.toString();
    }
}
