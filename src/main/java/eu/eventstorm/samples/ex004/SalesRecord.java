package eu.eventstorm.samples.ex004;

import java.sql.Date;

import eu.eventstorm.sql.annotation.Column;
import eu.eventstorm.sql.annotation.Flyway;
import eu.eventstorm.sql.annotation.PrimaryKey;
import eu.eventstorm.sql.annotation.Table;

@Table(value = "sales_record", pageable = true, flyway = @Flyway(version = "1.0.0.004" , description = "init_schema"))
public interface SalesRecord {

    @PrimaryKey("id")
    int getId();

    void setId(int id);

    @Column(value = "region", length = 54)
    String getRegion();

    void setRegion(String region);

    @Column(value ="country")
    String getCountry();

    void setCountry(String country);

    @Column(value ="item_type")
    String getItemType();

    void setItemType(String itemType);

    @Column(value ="sales_channel")
    String getSales();

    void setSales(String sales);

    @Column(value ="order_priority")
    String getOrderPriority();

    void setOrderPriority(String priority);

    @Column(value ="order_date")
    Date getOrderDate();

    void setOrderDate(Date date);

    @Column(value ="order_id")
    long getOrderId();

    void setOrderId(long orderId);

    @Column(value ="ship_date")
    Date getShipDate();

    void setShipDate(Date date);

    @Column(value ="unit_sold")
    double getUnitSold();

    void setUnitSold(double unitSold);

    @Column(value ="unit_price")
    double getUnitPrice();

    void setUnitPrice(double unitPrice);

    @Column(value ="unit_cost")
    double getUnitCost();

    void setUnitCost(double unitCost);

    @Column(value ="total_revenue")
    double getTotalRevenue();

    void setTotalRevenue(double totalRevenue);

    @Column(value ="total_cost")
    double getTotalCost();

    void setTotalCost(double totalCost);

    @Column(value ="total_profit")
    double getTotalProfit();

    void setTotalProfit(double totalProfit);

}