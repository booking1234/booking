/**
 * @author 		Mike Gavaghan - Gavaghan Geodesy
 * @version		1.1.1
 * @license 	This code may be freely used and modified on any personal or professional project.  It comes with no warranty.
 * @see			http://www.gavaghan.org/blog/free-source-code/geodesy-library-vincentys-formula/
 *
 * Utility methods for dealing with angles.
 */
package net.cbtltd.server.geodesy;

public class Angle
{
   /** Degrees/Radians conversion constant. */
   static private final double PiOver180 = Math.PI / 180.0;
   
   /**
    * Disallow instantiation.
    */
   private Angle()
   {
   }

   /**
    * Convert degrees to radians.
    * @param degrees
    * @return
    */
   static public double toRadians( double degrees )
   {
      return degrees * PiOver180;
   }
   
   /**
    * Convert radians to degrees.
    * @param radians
    * @return
    */
   static public double toDegrees( double radians )
   {
      return radians / PiOver180;
   }
}
