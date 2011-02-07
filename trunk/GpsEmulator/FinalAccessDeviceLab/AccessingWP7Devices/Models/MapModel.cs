using System;
using System.Linq;
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
using System.Collections.Generic;
using System.Runtime.Serialization;

using Microsoft.Phone.Controls.Maps;
using AccessingWP7Devices.Assets.Model;
using AccessingWP7Devices.Assets.Helpers;
using System.ComponentModel;
using System.Windows.Data;
using AccessingWP7Devices.Common;
using AccessingWP7Devices.Assets.Map;

namespace AccessingWP7Devices.Models
{
    [DataContract]
    public class MapModel : NotifyingObject
    {
        #region Consts

        /// <value>Default map zoom level.</value>
        private const double DefaultZoomLevel = 18.0;

        /// <value>Maximum map zoom level allowed.</value>
        private const double MaxZoomLevel = 21.0;

        /// <value>Minimum map zoom level allowed.</value>
        private const double MinZoomLevel = 1.0;

        #endregion

        #region Fields

        /// <value>Provides credentials for the map control.</value>
        private readonly CredentialsProvider _credentialsProvider = new ApplicationIdCredentialsProvider(App.BingId);

        private IList<Picture> _pictures;

        #endregion

        #region Properties

        [IgnoreDataMember]
        public CredentialsProvider CredentialsProvider
        {
            get { return _credentialsProvider; }
        }

        [IgnoreDataMember]
        public IEnumerable<Picture> Pictures
        {
            get
            {
                if (_pictures == null)
                {
                    var pictures = from picture in PictureRepository.Instance.Pictures
                                where picture.Position != GeoCoordinate.Unknown
                                select picture;

                    _pictures = pictures.ToArray();
                }

                return _pictures;
            }
        }        

        /// <summary>
        /// Gets or sets the map zoom level.
        /// </summary>
        [DataMember]
        public double Zoom
        {
            get { return GetValue(() => Zoom); }
            set
            {
                var coercedZoom = Math.Max(MinZoomLevel, Math.Min(MaxZoomLevel, value));
                SetValue(() => Zoom, coercedZoom);
            }
        }

        /// <summary>
        /// Gets or sets map center coordinate.
        /// </summary>
        [DataMember]
        public GeoCoordinate Center
        {
            get { return GetValue(() => Center); }
            set { SetValue(() => Center, value); }
        }

        /// <summary>
        /// Gets or sets the map mode.
        /// </summary>
        [DataMember]
        public MapMode Mode
        {
            get { return GetValue(() => Mode); }
            set { SetValue(() => Mode, value); }
        }

        /// <summary>
        /// Gets or sets my location coordinate.
        /// </summary>
        [DataMember]
        public GeoCoordinate MyLocation
        {
            get { return GetValue(() => MyLocation); }
            set
            {
                SetValue(() => MyLocation, value);
                if (TrackLocation)
                {
                    Center = value;
                }
            }
        }

        /// <summary>
        /// Update center is location changes.
        /// </summary>
        [DataMember]
        public bool TrackLocation
        {
            get { return GetValue(() => TrackLocation); }
            set { SetValue(() => TrackLocation, value); }
        }

        /// <summary>
        /// Gets or sets value indicating if map in preview mode.
        /// </summary>
        [DataMember]
        public bool InPreviewMode
        {
            get { return GetValue(() => InPreviewMode); }
            set { SetValue(() => InPreviewMode, value); }
        }

        /// <summary>
        /// Gets or sets the picture in preview.
        /// </summary>
        [DataMember]
        public Picture PreviewPicture
        {
            get { return GetValue(() => PreviewPicture); }
            set
            {
                InPreviewMode = value == null ? false : true;
                SetValue(() => PreviewPicture, value);
            }
        }

        #endregion       

        #region Ctor

        public MapModel()
        {
            Zoom = DefaultZoomLevel;
            Mode = MapMode.Aerial;
        }

        #endregion        
    }
}
