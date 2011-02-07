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
using Microsoft.Phone.Tasks;

using AccessingWP7Devices.Models;
using AccessingWP7Devices.Common;
using AccessingWP7Devices.Assets.Controls;

namespace AccessingWP7Devices.Pages
{
    public partial class PictureListPage : PhoneApplicationPage
    {
        #region Fields

        private readonly CameraCaptureTask _cameraTask;

        #endregion

        #region Ctor

        public PictureListPage()
        {
            DataContext = PictureRepository.Instance;

            InitializeComponent();

            // Initialize camera task
            _cameraTask = new CameraCaptureTask();
            _cameraTask.Completed += cameraTask_Completed;
        }

        #endregion

        #region Event Handlers

        protected override void OnNavigatedTo(System.Windows.Navigation.NavigationEventArgs e)
        {
            var capturedPicture = TransientState.Get<CapturedPicture>("capturedPicture");
            if (capturedPicture != null)
            {
                TransientState.Set<CapturedPicture>(CapturedPicturePage.ModelStateKey, capturedPicture);
                NavigationService.Navigate<CapturedPicturePage>();
            }            

            base.OnNavigatedTo(e);
        }

        private void cameraTask_Completed(object sender, PhotoResult e)
        {
            if (e.TaskResult == TaskResult.OK)
            {
                // Get the image temp file from e.OriginalFileName.
                // Get the image temp stream from e.ChosenPhoto.
                // Don't keep either the temp stream or file name.
                var capturedPicture = new CapturedPicture(e.OriginalFileName, e.ChosenPhoto);
                TransientState.Set("capturedPicture", capturedPicture);
            }
        }

        #endregion

        #region Application Bar

        private void ApplicationBarPictureList_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate<PictureListPage>();
        }

        private void ApplicationBarNewPicture_Click(object sender, EventArgs e)
        {
            CaptureNewImage();
        }

        private void ApplicationBarViewOnMap_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate<MapPage>();
        }

        private void ApplicationBarSlideShow_Click(object sender, EventArgs e)
        {
            NavigationService.Navigate<SlideShowPage>();
        }

        #endregion        

        #region Privates

        private void CaptureNewImage()
        {
            _cameraTask.Show();
        }

        #endregion
    }
}