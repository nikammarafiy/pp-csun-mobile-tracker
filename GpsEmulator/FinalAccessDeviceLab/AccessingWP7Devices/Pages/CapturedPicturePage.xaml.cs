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
using System.Device.Location;

using Microsoft.Phone.Controls;
using Microsoft.Phone.Controls.Maps;

using AccessingWP7Devices.Models;
using AccessingWP7Devices.Assets.Controls;
using AccessingWP7Devices.Common;
using AccessingWP7Devices.Assets.Helpers;
using Microsoft.Phone.Shell;
using System.Diagnostics;

namespace AccessingWP7Devices.Pages
{
    public partial class CapturedPicturePage : PhoneApplicationPage
    {
        #region Fields

        internal const string ModelStateKey = "CapturedPicturePage.Model";

        /// <value>Provides credentials for the map control.</value>
        private readonly CredentialsProvider _credentialsProvider = new ApplicationIdCredentialsProvider(App.BingId);

        #endregion

        #region Properties

        private CapturedPicture Model
        {
            get
            {
                if (DataContext == null)
                {
                    var model = TransientState.Get<CapturedPicture>(ModelStateKey, false);
                    if (model == null)
                    {
                        model = new CapturedPicture();
                        TransientState.Set(ModelStateKey, model);
                    }

                    DataContext = model;
                }

                return DataContext as CapturedPicture;
            }
        }

        #endregion

        #region Ctor

        public CapturedPicturePage()
        {
            InitializeComponent();            
        }

        #endregion

        #region Overrides

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            if (Model != null)
            {
                ResolvePictureAddress(Model);
            }
            
            base.OnNavigatedTo(e);
        }

        #endregion        

        #region Application Bar

        private void ApplicationBarPictureList_Click(object sender, EventArgs e)
        {
            NavigationService.GoBack();
        }

        private void ApplicationBarSavePicture_Click(object sender, EventArgs e)
        {
            NotificationBox.Show(
                "Save picture",
                "Where would you like to save the picture?",
                SaveToPicturesHub,
                SaveToLocalStorage);
        }

        #endregion

        #region Privates

        private void ResolvePictureAddress(CapturedPicture picture)
        {
            if (GpsHelper.Instance.Watcher.Status == GeoPositionStatus.Ready)
            {
                picture.Position = GpsHelper.Instance.Watcher.Position.Location;
                GeocodeHelper.ReverseGeocodeAddress(
                    Dispatcher,
                    _credentialsProvider,
                    picture.Position,
                    result => picture.Address = result.Address.FormattedAddress);
            }
            else
            {
                picture.Position = GeoCoordinate.Unknown;
            }            
        }

        private NotificationBoxCommand SaveToLocalStorage
        {
            get
            {
                return new NotificationBoxCommand("local store", () =>
                {
                    // Cache image in repository.
                    PictureRepository.Instance.Pictures.Add(Model);
                    PictureRepository.Instance.SaveToLocalStorage(Model, PictureRepository.IsolatedStoragePath);

                    NavigationService.GoBack();
                });
            }
        }

        private NotificationBoxCommand SaveToPicturesHub
        {
            get
            {
                return new NotificationBoxCommand("pictures hub", () =>
                {
                    PictureRepository.Instance.SaveToPicturesHub(Model);

                    NavigationService.GoBack();
                });
            }
        }

        #endregion
    }
}