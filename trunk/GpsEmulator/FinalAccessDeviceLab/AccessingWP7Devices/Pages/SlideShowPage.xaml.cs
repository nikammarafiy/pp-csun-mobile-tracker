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
using AccessingWP7Devices.Models;

namespace AccessingWP7Devices.Pages
{
    public partial class SlideShowPage : PhoneApplicationPage
    {
        #region Properties

        private SlideShowModel Model
        {
            get { return DataContext as SlideShowModel; }
            set { DataContext = value; }
        }        

        #endregion        

        #region Ctor

        public SlideShowPage()
        {
            Model = new SlideShowModel();

            InitializeComponent();
        }

        #endregion        

        #region Event Handlers

        private void Image_MouseLeftButtonDown(object sender, MouseButtonEventArgs e)
        {
            Model.IsRunning = false;
            ExitFullScreen();
        }

        #endregion

        #region Application Bar

        private void ApplicationBarPictureList_Click(object sender, EventArgs e)
        {
            NavigationService.GoBack();
        }

        private void ApplicationBarPlaySlow_Click(object sender, EventArgs e)
        {
            EnterFullScreen();
            Model.Mode = SlideShowMode.Slow;
            Model.IsRunning = true;
        }

        private void ApplicationBarPlayFast_Click(object sender, EventArgs e)
        {
            EnterFullScreen();
            Model.Mode = SlideShowMode.Fast;
            Model.IsRunning = true;
        }
        
        #endregion       

        #region Privates

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

        #endregion
    }
}