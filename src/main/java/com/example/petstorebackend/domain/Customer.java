package com.example.petstorebackend.domain;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

/**
 * Customer
 */

public class Customer   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("address")
  @Valid
  private List<Address> address = null;

  private Long createdTimeStamp;

  public Customer id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   *
   * @return id
   **/
  @Schema(example = "100000", description = "")
  
    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Customer username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   **/
  @Schema(example = "fehguy", description = "")
  
    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Customer address(List<Address> address) {
    this.address = address;
    return this;
  }

  public Customer addAddressItem(Address addressItem) {
    if (this.address == null) {
      this.address = new ArrayList<Address>();
    }
    this.address.add(addressItem);
    return this;
  }

  /**
   * Get address
   * @return address
   **/
  @Schema(description = "")
      @Valid
    public List<Address> getAddress() {
    return address;
  }

  public void setAddress(List<Address> address) {
    this.address = address;
  }

  public void setCreatedTimeStamp(Long createdTimeStamp) { this.createdTimeStamp = createdTimeStamp; }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Customer customer = (Customer) o;
    return Objects.equals(this.id, customer.id) &&
        Objects.equals(this.username, customer.username) &&
        Objects.equals(this.address, customer.address);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, address);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Customer {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
