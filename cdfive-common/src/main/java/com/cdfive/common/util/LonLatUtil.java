package com.cdfive.common.util;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * @author cdfive
 */
public class LonLatUtil {

    private static double PI = Math.PI;
    private static double TWOPI = Math.PI * 2;
    private static double DE2RA = 0.01745329252;
    private static double RA2DE = 57.2957795129;
    private static double ERAD = 6378.135;
    private static double ERADM = 6378135.0;
    private static double AVG_ERAD = 6371.0;
    private static double FLATTENING = 1.0 / 298.257223563;

    private static double EPS = 0.000000000005;
    private static double KM2MI = 0.621371;
    private static double GEOSTATIONARY_ALT = 35786.0; // km

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
        return IsPtInPoly(point, pointList);
    }

    /**
     * 判断点是否在多边形内，如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
     *
     * @param point 检测点
     * @param pts   多边形的顶点
     * @return 点在多边形内返回true, 否则返回false
     */
    public static boolean IsPtInPoly(Point2D.Double point, List<Point2D.Double> pts) {

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
