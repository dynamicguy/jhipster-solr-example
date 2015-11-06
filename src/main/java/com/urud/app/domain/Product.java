package com.urud.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.urud.app.domain.enumeration.Status;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SolrDocument(solrCoreName = "products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 512)
    @Column(name = "name", length = 512, nullable = false)
    @Field("name")
    private String name;

    @NotNull
    @Column(name = "url", nullable = false)
    @Field("url")
    private String url;

    @Column(name = "description")
    @Field("description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Field("status")
    private Status status;

    @Column(name = "in_stock")
    @Field("in_stock")
    private Boolean inStock;

    @Column(name = "price", precision=10, scale=2, nullable = false)
    @Field("price")
    private BigDecimal price;

    @Column(name = "keywords")
    @Field("keywords")
    private String keywords;

    @Column(name = "features")
    @Field("features")
    private String features;

    @Column(name = "popularity")
    @Field("popularity")
    private Long popularity;

    @Column(name = "location")
    @Field("location")
    private String location;

    @Column(name = "manufacture_date", nullable = false)
    @Field("manufacture_date")
    private ZonedDateTime manufactureDate;

    @Lob
    @Column(name = "image")
    @Field("image")
    private byte[] image;


    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;
    @Column(name = "weight", precision=10, scale=2, nullable = false)
    @Field("weight")
    private BigDecimal weight;

    @NotNull
    @Column(name = "sku", nullable = false)
    @Field("sku")
    private String sku;

    @Column(name = "includes")
    @Field("includes")
    private String includes;

    @Column(name = "incubation_date", nullable = false)
    @Field("incubation_date")
    private ZonedDateTime incubationDate;

    @ManyToOne
    private User user;

    @ManyToMany    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_cat",
               joinColumns = @JoinColumn(name="products_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="cats_id", referencedColumnName="ID"))
    private Set<Cat> cats = new HashSet<>();

    @ManyToMany    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_manufacturer",
               joinColumns = @JoinColumn(name="products_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="manufacturers_id", referencedColumnName="ID"))
    private Set<Manufacturer> manufacturers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ZonedDateTime getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(ZonedDateTime manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getIncludes() {
        return includes;
    }

    public void setIncludes(String includes) {
        this.includes = includes;
    }

    public ZonedDateTime getIncubationDate() {
        return incubationDate;
    }

    public void setIncubationDate(ZonedDateTime incubationDate) {
        this.incubationDate = incubationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Cat> getCats() {
        return cats;
    }

    public void setCats(Set<Cat> cats) {
        this.cats = cats;
    }

    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Set<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Product product = (Product) o;

        if ( ! Objects.equals(id, product.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", url='" + url + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            ", inStock='" + inStock + "'" +
            ", price='" + price + "'" +
            ", keywords='" + keywords + "'" +
            ", features='" + features + "'" +
            ", popularity='" + popularity + "'" +
            ", location='" + location + "'" +
            ", manufactureDate='" + manufactureDate + "'" +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", weight='" + weight + "'" +
            ", sku='" + sku + "'" +
            ", includes='" + includes + "'" +
            ", incubationDate='" + incubationDate + "'" +
            '}';
    }
}
