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
using System.Runtime.Serialization;
using System.Collections.Generic;
using System.Windows.Threading;
using System.Collections.ObjectModel;
using AccessingWP7Devices.Assets.Model;

namespace AccessingWP7Devices.Models
{    
    [DataContract]
    public class SlideShowModel : NotifyingObject
    {
        #region Fields

        private DispatcherTimer _slideShowTimer;
        
        #endregion

        #region Properties

        [IgnoreDataMember]
        public DispatcherTimer SlideShowTimer
        {
            get
            {
                if (_slideShowTimer == null)
                {
                    _slideShowTimer = new DispatcherTimer();
                    _slideShowTimer.Tick += slideShowTimer_Tick;
                }

                return _slideShowTimer;
            }
        }

        [IgnoreDataMember]
        public ObservableCollection<Picture> Pictures
        {
            get { return PictureRepository.Instance.Pictures; }
        }

        [DataMember]
        public int CurrentPictureIndex { get; set; }

        [IgnoreDataMember]
        public Picture CurrentPicture
        {
            get { return Pictures[CurrentPictureIndex]; }
        }

        [DataMember]
        public int Mode
        {
            get { return GetValue(() => Mode); }
            set
            {
                SetValue(() => Mode, value);
                SlideShowTimer.Interval = TimeSpan.FromSeconds(value);
            }
        }

        /// <summary>
        /// Gets or sets value indicating if slide show is running.
        /// </summary>
        [DataMember]
        public bool IsRunning
        {
            get { return GetValue(() => IsRunning); }
            set
            {
                SetValue(() => IsRunning, value);
                if (value)
                {
                    SlideShowTimer.Start();
                }
                else
                {
                    SlideShowTimer.Stop();
                }
            }
        }
        
        #endregion

        #region Ctor

        public SlideShowModel()
        {
            SlideShowTimer.Tick += slideShowTimer_Tick;
        }

        private void slideShowTimer_Tick(object sender, EventArgs e)
        {
            if (++CurrentPictureIndex >= Pictures.Count)
            {
                CurrentPictureIndex = 0;
            }

            NotifyPropertyChanged(() => CurrentPicture);
        }
        
        #endregion
    }
}
