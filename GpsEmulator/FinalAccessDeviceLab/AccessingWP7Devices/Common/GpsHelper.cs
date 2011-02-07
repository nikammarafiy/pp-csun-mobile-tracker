#define GPS_EMULATOR

using System;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using System.Device.Location;

namespace AccessingWP7Devices.Common
{
    public class GpsHelper
    {
        #region Fields

        private static GpsHelper _instance;
        
        #endregion

        #region Properties

        public IGeoPositionWatcher<GeoCoordinate> Watcher { get; private set; }        

        public static GpsHelper Instance
        {
            get
            {
                WarmUp();                    

                return _instance;
            }
        }        

        #endregion

        #region Operations

        public static void WarmUp()
        {
            if (_instance == null)
            {
                _instance = new GpsHelper();
                _instance.InitializeGpsDevice();
            }
        }
        
        #endregion

        #region Privates
       
        private void InitializeGpsDevice()
        {
            try
            {
                if (Watcher == null)
                {
#if GPS_EMULATOR
                    Watcher = new GpsEmulatorClient.GeoCoordinateWatcher();
#else
                    Watcher = new GeoCoordinateWatcher();
#endif
                }

                Watcher.Start();
            }
            catch (Exception ex)
            {
                MessageBox.Show(string.Format("Failed to initialize GPS device:{0}", ex.Message), "GPS Error", MessageBoxButton.OK);
            }
        }        
        
        #endregion

        #region Ctor

        private GpsHelper()
        {
        }

        #endregion                   
    }
}
