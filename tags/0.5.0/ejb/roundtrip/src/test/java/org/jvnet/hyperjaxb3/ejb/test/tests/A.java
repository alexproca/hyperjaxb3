package org.jvnet.hyperjaxb3.ejb.test.tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jvnet.hyperjaxb3.item.Item;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.hyperjaxb3.xml.bind.JAXBContextUtils;
import org.jvnet.hyperjaxb3.xml.bind.JAXBElementUtils;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDateTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.builder.JAXBEqualsBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "A", propOrder = { "id", "b", "b1", "b2", "d", "e",
		"eNillable", "f", "fNillable", "g", "h" })
@Entity
@Table(name = "table_a")
public class A implements Equals {

	@XmlAttribute
	private String id;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private B b;

	@ManyToOne(cascade = { CascadeType.ALL })
	public B getB() {
		return b;
	}

	public void setB(B b) {
		this.b = b;
	}

	private List<B> b1 = new LinkedList<B>();

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "A_B1")
	public List<B> getB1() {
		return b1;
	}

	public void setB1(List<B> b1) {
		this.b1 = b1;
	}

	private List<B> b2 = new LinkedList<B>();

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "A_B2")
	public List<B> getB2() {
		return b2;
	}

	public void setB2(List<B> b2) {
		this.b2 = b2;
	}

	private String d;

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	protected List<String> e;

	@Transient
	public List<String> getE() {
		if (eItems == null) {
			eItems = new ArrayList<EItem>();
		}
		if (ItemUtils.shouldBeWrapped(e))
			e = ItemUtils.wrap(e, eItems, EItem.class);
		return this.e;
	}

	@XmlTransient
	private List<EItem> eItems;

	@OneToMany(cascade = CascadeType.ALL)
	public List<EItem> getEItems() {
		if (eItems == null) {
			eItems = new ArrayList<EItem>();
		}
		if (ItemUtils.shouldBeWrapped(e))
			e = ItemUtils.wrap(e, eItems, EItem.class);
		return this.eItems;
	}

	public void setEItems(List<EItem> eItems) {
		this.eItems = eItems;
		if (ItemUtils.shouldBeWrapped(e))
			e = ItemUtils.wrap(e, eItems, EItem.class);
	}

	@XmlElementRef(name = "eNillable", type = JAXBElement.class)
	protected List<JAXBElement<String>> eNillable;

	@Transient
	public List<JAXBElement<String>> getENillable() {
		if (eNillableItems == null) {
			eNillableItems = new ArrayList<ENillableItem>();
		}

		if (ItemUtils.shouldBeWrapped(eNillable))
			eNillable = ItemUtils.wrap(eNillable, eNillableItems,
					ENillableItem.class);
		return this.eNillable;
	}

	@XmlTransient
	private List<ENillableItem> eNillableItems;

	@OneToMany(cascade = CascadeType.ALL)
	public List<ENillableItem> getENillableItems() {
		if (eNillableItems == null) {
			eNillableItems = new ArrayList<ENillableItem>();
		}
		if (ItemUtils.shouldBeWrapped(eNillable))
			eNillable = ItemUtils.wrap(eNillable, eNillableItems,
					ENillableItem.class);
		return this.eNillableItems;
	}

	public void setENillableItems(List<ENillableItem> eNillableItems) {
		this.eNillableItems = eNillableItems;
		if (ItemUtils.shouldBeWrapped(eNillable))
			eNillable = ItemUtils.wrap(eNillable, eNillableItems,
					ENillableItem.class);
	}

	protected List<XMLGregorianCalendar> f;

	@Transient
	public List<XMLGregorianCalendar> getF() {
		if (fItems == null) {
			fItems = new ArrayList<FItem>();
		}
		if (ItemUtils.shouldBeWrapped(f))
			f = ItemUtils.wrap(f, fItems, FItem.class);
		return this.f;
	}

	@XmlTransient
	private List<FItem> fItems;

	@OneToMany(cascade = CascadeType.ALL)
	public List<FItem> getFItems() {
		if (fItems == null) {
			fItems = new ArrayList<FItem>();
		}
		if (ItemUtils.shouldBeWrapped(f))
			f = ItemUtils.wrap(f, fItems, FItem.class);
		return this.fItems;
	}

	public void setFItems(List<FItem> fItems) {
		this.fItems = fItems;
		if (ItemUtils.shouldBeWrapped(f))
			f = ItemUtils.wrap(f, fItems, FItem.class);
	}

	@XmlElementRef(name = "fNillable", type = JAXBElement.class)
	protected List<JAXBElement<XMLGregorianCalendar>> fNillable;

	@Transient
	public List<JAXBElement<XMLGregorianCalendar>> getFNillable() {
		if (fNillableItems == null) {
			fNillableItems = new ArrayList<FNillableItem>();
		}

		if (ItemUtils.shouldBeWrapped(fNillable))
			fNillable = ItemUtils.wrap(fNillable, fNillableItems,
					FNillableItem.class);
		return this.fNillable;
	}

	@XmlTransient
	private List<FNillableItem> fNillableItems;

	@OneToMany(cascade = CascadeType.ALL)
	public List<FNillableItem> getFNillableItems() {
		if (fNillableItems == null) {
			fNillableItems = new ArrayList<FNillableItem>();
		}
		if (ItemUtils.shouldBeWrapped(fNillable))
			fNillable = ItemUtils.wrap(fNillable, fNillableItems,
					FNillableItem.class);
		return this.fNillableItems;
	}

	public void setFNillableItems(List<FNillableItem> fNillableItems) {
		this.fNillableItems = fNillableItems;
		if (ItemUtils.shouldBeWrapped(fNillable))
			fNillable = ItemUtils.wrap(fNillable, fNillableItems,
					FNillableItem.class);
	}

	@Override
	public boolean equals(Object obj) {
		final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
		equals(obj, equalsBuilder);
		return equalsBuilder.isEquals();
	}

	public void equals(Object object, EqualsBuilder equalsBuilder) {
		if (!(object instanceof A)) {
			equalsBuilder.appendSuper(false);
			return;
		}
		if (this == object) {
			return;
		}
		final A that = (A) object;
		equalsBuilder.append(this.getId(), that.getId());
		equalsBuilder.append(this.getB(), that.getB());
		equalsBuilder.append(this.getB1(), that.getB1());
		equalsBuilder.append(this.getB2(), that.getB2());
		equalsBuilder.append(this.getD(), that.getD());
		equalsBuilder.append(this.getE(), that.getE());
		equalsBuilder.append(this.getENillable(), that.getENillable());
		equalsBuilder.append(this.getF(), that.getF());
		equalsBuilder.append(this.getFNillable(), that.getFNillable());
		equalsBuilder.append(this.getG(), that.getG());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "EItem")
	@Entity
	public static class EItem extends PrimitiveItem<String, String> {

		@XmlAttribute
		protected Long hjid;

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		public Long getHjid() {
			return hjid;
		}

		public void setHjid(Long value) {
			this.hjid = value;
		}

		public void setItem(String value) {
			setValue(value);
		}

		public String getItem() {
			return getValue();
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "ENillableItem")
	@Entity
	public static class ENillableItem extends
			PrimitiveItem<String, JAXBElement<String>> {

		@XmlAttribute
		protected Long hjid;

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		public Long getHjid() {
			return hjid;
		}

		public void setHjid(Long value) {
			this.hjid = value;
		}

		public void setItem(JAXBElement<String> value) {
			setValue(XmlAdapterUtils.unmarshallJAXBElement(value));

		}

		public JAXBElement<String> getItem() {
			return XmlAdapterUtils.marshallJAXBElement(String.class, new QName(
					"eNillable"), A.class, getValue());
		}

	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "FItem")
	@Entity
	public static class FItem extends PrimitiveItem<Date, XMLGregorianCalendar> {

		@XmlAttribute
		protected Long hjid;

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		public Long getHjid() {
			return hjid;
		}

		public void setHjid(Long value) {
			this.hjid = value;
		}

		@Temporal(TemporalType.TIMESTAMP)
		public Date getItemAsDate() {
			return XmlAdapterUtils.unmarshall(
					XMLGregorianCalendarAsDateTime.class, this.getItem());
		}

		public void setItemAsDate(Date target) {
			this.setItem(XmlAdapterUtils.marshall(
					XMLGregorianCalendarAsDateTime.class, target));
		}

		public void setItem(XMLGregorianCalendar value) {
			setValue(XmlAdapterUtils.unmarshall(
					XMLGregorianCalendarAsDateTime.class, value));

		}

		@Transient
		public XMLGregorianCalendar getItem() {
			return XmlAdapterUtils.marshall(
					XMLGregorianCalendarAsDateTime.class, getValue());
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "FItemNillable")
	@Entity
	public static class FNillableItem extends
			PrimitiveItem<Date, JAXBElement<XMLGregorianCalendar>> {

		@XmlAttribute
		protected Long hjid;

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		public Long getHjid() {
			return hjid;
		}

		public void setHjid(Long value) {
			this.hjid = value;
		}

		@Temporal(TemporalType.TIMESTAMP)
		public Date getItemAsDate() {
			return XmlAdapterUtils.unmarshallJAXBElement(
					XMLGregorianCalendarAsDateTime.class, this.getItem());
		}

		public void setItemAsDate(Date target) {
			this.setItem(XmlAdapterUtils.marshallJAXBElement(
					XMLGregorianCalendarAsDateTime.class,
					XMLGregorianCalendar.class, new QName("fNillable"),
					A.class, target));
		}

		public void setItem(JAXBElement<XMLGregorianCalendar> value) {
			setValue(XmlAdapterUtils.unmarshallJAXBElement(
					XMLGregorianCalendarAsDateTime.class, value));

		}

		@Transient
		public JAXBElement<XMLGregorianCalendar> getItem() {
			return XmlAdapterUtils.marshallJAXBElement(
					XMLGregorianCalendarAsDateTime.class,
					XMLGregorianCalendar.class, new QName("fNillable"),
					A.class, getValue());
		}

	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "G", propOrder = { "h" })
	@XmlSeeAlso( { G1.class, G2.class })
	@Entity
	@Inheritance(strategy = InheritanceType.JOINED)
	public static class G {

		@XmlAttribute
		protected Long hjid;

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		public Long getHjid() {
			return hjid;
		}

		public void setHjid(Long value) {
			this.hjid = value;
		}

		private String h;

		@Basic
		public String getH() {
			return h;
		}

		public void setH(String h) {
			this.h = h;
		}

		@Override
		public boolean equals(Object obj) {
			final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
			equals(obj, equalsBuilder);
			return equalsBuilder.isEquals();
		}

		public void equals(Object object, EqualsBuilder equalsBuilder) {
			if (!(object instanceof G)) {
				equalsBuilder.appendSuper(false);
				return;
			}
			if (this == object) {
				return;
			}
			final G that = (G) object;
			equalsBuilder.append(this.getH(), that.getH());
		}

	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "G1", propOrder = { "h1" })
	@Entity
	public static class G1 extends G {
		private String h1;

		@Basic
		public String getH1() {
			return h1;
		}

		public void setH1(String h1) {
			this.h1 = h1;
		}

		@Override
		public boolean equals(Object obj) {
			final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
			equals(obj, equalsBuilder);
			return equalsBuilder.isEquals();
		}

		public void equals(Object object, EqualsBuilder equalsBuilder) {
			if (!(object instanceof G1)) {
				equalsBuilder.appendSuper(false);
				return;
			}
			if (this == object) {
				return;
			}
			final G1 that = (G1) object;
			equalsBuilder.append(this.getH(), that.getH());
			equalsBuilder.append(this.getH1(), that.getH1());
		}

	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "g2", propOrder = { "h2" })
	@Entity
	public static class G2 extends G {
		private String h2;

		@Basic
		public String getH2() {
			return h2;
		}

		public void setH2(String h2) {
			this.h2 = h2;
		}

		@Override
		public boolean equals(Object obj) {
			final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
			equals(obj, equalsBuilder);
			return equalsBuilder.isEquals();
		}

		public void equals(Object object, EqualsBuilder equalsBuilder) {
			if (!(object instanceof G2)) {
				equalsBuilder.appendSuper(false);
				return;
			}
			if (this == object) {
				return;
			}
			final G2 that = (G2) object;
			equalsBuilder.append(this.getH(), that.getH());
			equalsBuilder.append(this.getH2(), that.getH2());
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "GItem")
	@Entity
	public static class GItem implements Item<JAXBElement<? extends G>> {

		@XmlAttribute
		protected Long hjid;

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		public Long getHjid() {
			return hjid;
		}

		public void setHjid(Long value) {
			this.hjid = value;
		}

		private String itemName;

		@Basic
		public String getItemName() {
			return itemName;
		}

		public void setItemName(String name) {
			this.itemName = name;
		}

		private G itemValue;

		@ManyToOne(cascade = { CascadeType.ALL })
		public G getItemValue() {
			return itemValue;
		}

		public void setItemValue(G g) {
			this.itemValue = g;
		}

		private JAXBElement<? extends G> item;

		@Transient
		public JAXBElement<? extends G> getItem() {
			if (JAXBElementUtils.shouldBeWrapped(item, itemName, itemValue)) {
				item = JAXBElementUtils.wrap(item, itemName, itemValue);
			}
			return item;
		}

		public void setItem(JAXBElement<? extends G> value) {
			this.itemName = value.getName().toString();
			this.itemValue = value.getValue();
			this.item = value;
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		@Override
		public boolean equals(Object obj) {
			final EqualsBuilder equalsBuilder = new JAXBEqualsBuilder();
			equals(obj, equalsBuilder);
			return equalsBuilder.isEquals();
		}

		public void equals(Object object, EqualsBuilder equalsBuilder) {
			if (!(object instanceof GItem)) {
				equalsBuilder.appendSuper(false);
				return;
			}
			if (this == object) {
				return;
			}
			final GItem that = (GItem) object;
			equalsBuilder.append(this.getItemValue(), that.getItemValue());
		}
	}

	@XmlElementRef(name = "g", namespace = "", type = JAXBElement.class)
	protected List<JAXBElement<? extends G>> g;

	@Transient
	public List<JAXBElement<? extends G>> getG() {
		if (gItems == null) {
			gItems = new ArrayList<GItem>();
		}
		if (ItemUtils.shouldBeWrapped(g))
			g = ItemUtils.wrap(g, gItems, GItem.class);
		return this.g;
	}

	@XmlTransient
	private List<GItem> gItems;

	@OneToMany(cascade = CascadeType.ALL)
	public List<GItem> getGItems() {
		if (gItems == null) {
			gItems = new ArrayList<GItem>();
		}
		if (ItemUtils.shouldBeWrapped(g))
			g = ItemUtils.wrap(g, gItems, GItem.class);
		return this.gItems;
	}

	public void setGItems(List<GItem> gItems) {
		this.gItems = gItems;
		if (ItemUtils.shouldBeWrapped(g))
			g = ItemUtils.wrap(g, gItems, GItem.class);
	}

	@XmlAnyElement(lax = true)
	protected Object h;

	@Transient
	public Object getH() {
		return h;
	}

	public void setH(Object h) {
		this.h = h;
	}

	@Basic
	public String getHItem() {
		return JAXBContextUtils.unmarshall(
				"org.jvnet.hyperjaxb3.ejb.test.tests", getH());
	}

	public void setHItem(String h) {
		setH(JAXBContextUtils
				.marshall("org.jvnet.hyperjaxb3.ejb.test.tests", h));
	}

}
