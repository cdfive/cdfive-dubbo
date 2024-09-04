package com.cdfive.common.util;

import com.cdfive.common.vo.GeoPointVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author cdfive
 */
@Slf4j
public class LonLatUtil {

    // 经度最小值
    private static final BigDecimal LON_MIN = new BigDecimal("-180");
    // 经度最大值
    private static final BigDecimal LON_MAX = new BigDecimal("180");
    // 纬度最小值
    private static final BigDecimal LAT_MIN = new BigDecimal("-90");
    // 纬度最大值
    private static final BigDecimal LAT_MAX = new BigDecimal("90");
    // 经纬度小数点后保留6位数
    private static final int LON_LAT_SCALE = 6;

    // 用于坐标系转换的常量
    private static final double X_PI = Math.PI * 3000.0 / 180.0;

    private static final double PI = Math.PI;
    private static final double TWOPI = Math.PI * 2;
    private static final double DE2RA = 0.01745329252;
    private static final double RA2DE = 57.2957795129;
    private static final double ERAD = 6378.135;
    private static final double ERADM = 6378135.0;
    private static final double AVG_ERAD = 6371.0;
    private static final double FLATTENING = 1.0 / 298.257223563;

    private static final double EPS = 0.000000000005;
    private static final double KM2MI = 0.621371;
    private static final double GEOSTATIONARY_ALT = 35786.0; // km

    /**
     * 验证经纬度合法
     *
     * @param lon 经度
     * @param lat 纬度
     * @return 是否合法
     */
    public static boolean validateLonLat(BigDecimal lon, BigDecimal lat) {
        if (lon == null || lat == null) {
            return false;
        }

        if (lon.compareTo(LON_MIN) < 0 || lon.compareTo(LON_MAX) > 0
                || lat.compareTo(LAT_MIN) < 0 || lat.compareTo(LAT_MAX) > 0) {
            return false;
        }

        return true;
    }

    /**
     * 字符串值转换为BigDecimal类型
     *
     * @param value String类型的经度或纬度值
     * @return 解析后BigDecimal类型的值
     */
    public static BigDecimal parseLonLat(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return new BigDecimal(value.trim());
    }

    /**
     * 经纬度转换,火星坐标系转百度坐标系
     */
    public static GeoPointVo marsToBaiduCoordinate(GeoPointVo geoPointVo) {
        if (geoPointVo == null) {
            return null;
        }

        if (geoPointVo.getLon() == null || geoPointVo.getLat() == null) {
            return geoPointVo;
        }

        try {
            double lon = geoPointVo.getLon().doubleValue();
            double lat = geoPointVo.getLat().doubleValue();
            double x = lon;
            double y = lat;
            double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
            double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
            double lonBaidu = z * Math.cos(theta) + 0.0065;
            double latBaidu = z * Math.sin(theta) + 0.006;
            return new GeoPointVo(new BigDecimal(String.valueOf(lonBaidu)).setScale(LON_LAT_SCALE, BigDecimal.ROUND_HALF_UP)
                    , new BigDecimal(String.valueOf(latBaidu)).setScale(LON_LAT_SCALE, BigDecimal.ROUND_HALF_UP));
        } catch (Exception e) {
            log.error("marsToBaiduCoordinate error,lon={},lat={}", geoPointVo.getLon(), geoPointVo.getLat(), e);
            return geoPointVo;
        }
    }

    /**
     * 经纬度转换,百度坐标系转火星坐标系
     */
    public static GeoPointVo baiduToMarsCoordinate(GeoPointVo geoPointVo) {
        if (geoPointVo == null) {
            return null;
        }

        if (geoPointVo.getLon() == null || geoPointVo.getLat() == null) {
            return geoPointVo;
        }

        try {
            double lon = geoPointVo.getLon().doubleValue();
            double lat = geoPointVo.getLat().doubleValue();
            double x = lon - 0.0065;
            double y = lat - 0.006;
            double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
            double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
            double lonMars = z * Math.cos(theta);
            double latMars = z * Math.sin(theta);
            return new GeoPointVo(new BigDecimal(String.valueOf(lonMars)).setScale(LON_LAT_SCALE, BigDecimal.ROUND_HALF_UP)
                    , new BigDecimal(String.valueOf(latMars)).setScale(LON_LAT_SCALE, BigDecimal.ROUND_HALF_UP));
        } catch (Exception e) {
            log.error("baiduToMarsCoordinate error,lon={},lat={}", geoPointVo.getLon(), geoPointVo.getLat(), e);
            return geoPointVo;
        }
    }

    /**
     * 计算2个经纬度坐标的距离
     */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = DE2RA * lat1;
        lon1 = -DE2RA * lon1;
        lat2 = DE2RA * lat2;
        lon2 = -DE2RA * lon2;

        double F = (lat1 + lat2) / 2.0;
        double G = (lat1 - lat2) / 2.0;
        double L = (lon1 - lon2) / 2.0;

        double sing = Math.sin(G);//0
        double cosl = Math.cos(L);//1
        double cosf = Math.cos(F);
        double sinl = Math.sin(L);//0
        double sinf = Math.sin(F);
        double cosg = Math.cos(G);//1

        double S = sing * sing * cosl * cosl + cosf * cosf * sinl * sinl;
        double C = cosg * cosg * cosl * cosl + sinf * sinf * sinl * sinl;
        double W = Math.atan2(Math.sqrt(S), Math.sqrt(C));
        double R = Math.sqrt((S * C)) / W;
        double H1 = (3 * R - 1.0) / (2.0 * C);
        double H2 = (3 * R + 1.0) / (2.0 * S);
        double D = 2 * W * ERAD;
        double result = (D * (1 + FLATTENING * H1 * sinf * sinf * cosg * cosg - FLATTENING * H2 * cosf * cosf * sing * sing)) * 1000;
        return Double.isNaN(result) ? 0d : result;
    }

    /**
     * @param lon               经度/Y轴
     * @param lat               纬度/X轴
     * @param partitionLocation 顶点(区域)经纬度集合
     * @return
     */
    public static boolean twoLonLat(String lon, String lat, String partitionLocation) {
        Map<String, String> orderLocation = new HashMap<>();

        orderLocation.put("X", lat);
        orderLocation.put("Y", lon);
        // 商业区域（百度多边形区域经纬度集合）
        // String partitionLocation = "40.057307_116.30142,40.061684_116.305337,40.060745_116.307672,40.060234_116.307439,40.060165_116.309451,40.060137_116.310745";
        return isInPolygon(orderLocation, partitionLocation);
    }

    /**
     * 是否在区域内
     */
    public static boolean isInPolygon(Map<String, String> orderLocation, String partitionLocation) {
        double p_x = Double.parseDouble(orderLocation.get("X"));
        double p_y = Double.parseDouble(orderLocation.get("Y"));
        Point2D.Double point = new Point2D.Double(p_x, p_y);

        List<Point2D.Double> pointList = new ArrayList<Point2D.Double>();
        List<String> strList = Arrays.asList(partitionLocation.split(","));

        for (String str : strList) {
            String[] points = str.split("-");
            double polygonPoint_x = Double.parseDouble(points[1]);
            double polygonPoint_y = Double.parseDouble(points[0]);
            Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x, polygonPoint_y);
            pointList.add(polygonPoint);
        }
        return isPtInPoly(point, pointList);
    }

    /**
     * 判断点是否在多边形内，如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
     *
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean isPtInPoly(Point2D.Double point, List<Point2D.Double> pts) {

        int N = pts.size();
        boolean boundOrVertex = true; //如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
        int intersectCount = 0;//cross points count of x
        double precision = 2e-10; //浮点类型计算时候与0比较时候的容差
        Point2D.Double p1, p2;//neighbour bound vertices
        Point2D.Double p = point; //当前点

        p1 = pts.get(0);//left vertex
        for (int i = 1; i <= N; ++i) {//check all rays
            if (p.equals(p1)) {
                return boundOrVertex;//p is an vertex
            }

            p2 = pts.get(i % N);//right vertex
            if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {//ray is outside of our interests
                p1 = p2;
                continue;//next ray left point
            }

            if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {//ray is crossing over by the algorithm (common part of)
                if (p.y <= Math.max(p1.y, p2.y)) {//x is before of ray
                    if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {//overlies on a horizontal ray
                        return boundOrVertex;
                    }

                    if (p1.y == p2.y) {//ray is vertical
                        if (p1.y == p.y) {//overlies on a vertical ray
                            return boundOrVertex;
                        } else {//before ray
                            ++intersectCount;
                        }
                    } else {//cross point on the left side
                        double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;//cross point of y
                        if (Math.abs(p.y - xinters) < precision) {//overlies on a ray
                            return boundOrVertex;
                        }

                        if (p.y < xinters) {//before ray
                            ++intersectCount;
                        }
                    }
                }
            } else {//special case when ray is crossing through the vertex
                if (p.x == p2.x && p.y <= p2.y) {//p crossing over p2
                    Point2D.Double p3 = pts.get((i + 1) % N); //next vertex
                    if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {//p.x lies between p1.x & p3.x
                        ++intersectCount;
                    } else {
                        intersectCount += 2;
                    }
                }
            }
            p1 = p2;//next ray left point
        }

        if (intersectCount % 2 == 0) {//偶数在多边形外
            return false;
        } else { //奇数在多边形内
            return true;
        }
    }
}
