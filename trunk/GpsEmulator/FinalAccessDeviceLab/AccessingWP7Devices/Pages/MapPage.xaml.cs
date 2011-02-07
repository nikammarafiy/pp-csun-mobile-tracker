using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.Phone.Controls;
using System.Device.Location;

using AccessingWP7Devices.Models;
using AccessingWP7Devices.Common;
using Microsoft.Phone.Controls.Maps;
using Microsoft.Phone.Shell;
using AccessingWP7Devices.Assets.Controls;
using AccessingWP7Devices.Assets.Map;

namespace AccessingWP7Devices.Pages
{
    public partial class MapPage : PhoneApplicationPage
    {
        #region Fields

        internal const string ModelStateKey = "MapPage.Model";

        private Picture _centeredPicture;
        
        #endregion

        #region Properties

        private MapModel Model
        {
            get
            {
                if (DataContext == null)
                {
                    var model = TransientState.Get<MapModel>(ModelStateKey, false);
                    if (model == null)
                    {
                        model = new MapModel();
                        TransientState.Set(ModelStateKey, model);
                    }

                    DataContext = model;
                }

                return DataContext as MapModel;
            }
        }

        #endregion

        #region Ctor

        public MapPage()
        {
            InitializeComponent();
            InitializeLocation();
        }

        #endregion        

        #region Overrides

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            GpsHelper.Instance.Watcher.PositionChanged += Watcher_PositionChanged;

            base.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(System.Windows.Navigation.NavigationEventArgs e)
        {
            GpsHelper.Instance.Watcher.PositionChanged -= Watcher_PositionChanged;

            base.OnNavigatedFrom(e);
        }
        
        #endregion

        #region Event Handlers

        private void Watcher_PositionChanged(object sender, GeoPositionChangedEventArgs<GeoCoordinate> e)
        {
            var position = e.Position.Location;
            if (position != GeoCoordinate.Unknown)
            {
                Model.MyLocation = e.Position.Location;
            }
        }

        private void ButtonZoomIn_Click(object sender, RoutedEventArgs e)
        {
            Model.Zoom += 1;
        }

        private void ButtonZoomOut_Click(object sender, RoutedEventArgs e)
        {
            Model.Zoom -= 1;
        }        

        private void picturesLayer_MouseLeftButtonUp(object sender, MouseButtonEventArgs e)
        {            
            var visualItem = e.OriginalSource as FrameworkElement;
            if (visualItem == null)
            {
                return;
            }

            var picture = visualItem.DataContext as Picture;
            if (_centeredPicture != picture)
            {
                _centeredPicture = picture;
                if (_centeredPicture != null)
                {
                    Model.Center = _centeredPicture.Position;
                }
            }
            else
            {
                Model.PreviewPicture = _centeredPicture;
                EnterFullScreen();
            }
        }

        private void previewPicture_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            Model.PreviewPicture = null;
            ExitFullScreen();
        }

        #endregion

        #region Application Bar

        private void ApplicationBarPictureList_Click(object sender, EventArgs e)
        {
            NavigationService.GoBack();
        }

        private void ApplicationBarMyLocation_Click(object sender, EventArgs e)
        {
            Model.Center = Model.MyLocation;
        }

        private void ApplicationBarTrackLocation_Click(object sender, EventArgs e)
        {
            Model.TrackLocation = !Model.TrackLocation;
        }

        private void ApplicationBarMapMode_Click(object sender, EventArgs e)
        {
            SwapMapMode();
        }

        #endregion

        #region Privates

        private void InitializeLocation()
        {
            var defaultCoordinate = new GeoCoordinate(47.639631, -122.127713);
            Model.MyLocation = defaultCoordinate;
            Model.Center = defaultCoordinate;            
        }

        private void EnterFullScreen()
        {
            TitlePanel.Visibility = Visibility.Collapsed;
            ApplicationBar.IsVisible = false;
        }

        private void ExitFullScreen()
        {
            TitlePanel.Visibility = Visibility.Visible;
            ApplicationBar.IsVisible = true;
        }

        private void SwapMapMode()
        {            
            if (Model.Mode == MapMode.Aerial)
            {
                Model.Mode = MapMode.Road;
            }
            else
            {
                Model.Mode = MapMode.Aerial;
            }
        }            

        #endregion
    }
}