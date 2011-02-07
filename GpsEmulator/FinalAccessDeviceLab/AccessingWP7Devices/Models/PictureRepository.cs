using System;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.IO.IsolatedStorage;
using System.Windows.Media.Imaging;
using System.IO;
using System.Runtime.Serialization;

using Microsoft.Xna.Framework.Media;

using AccessingWP7Devices.Common;
using AccessingWP7Devices.Assets.Model;

namespace AccessingWP7Devices.Models
{
    public class PictureRepository : NotifyingObject
    {
        #region Constants

        public const string IsolatedStoragePath = "Pictures";

	    #endregion

        #region Fields

        private readonly ObservableCollection<Picture> _pictures = new ObservableCollection<Picture>();

        #endregion

        #region Properties

        public ObservableCollection<Picture> Pictures
        {
            get { return _pictures; }
        }        

        #endregion

        #region Operations

        private void LoadSampleImages()
        {
            var samplePicturesResource = new ResourceDictionary
            {
                Source = new Uri("/AccessingWP7Devices;component/Resources/Styles/SamplePictures.xaml", UriKind.Relative)
            };
            
            foreach (ResourcePicture resourcePicture in samplePicturesResource.Values)
            {
                _pictures.Add(resourcePicture);
            }
        }

        /// <summary>
        /// Load all user saved pictures from the application's isolated storage.
        /// </summary>
        private void LoadAllPicturesFromIsolatedStorage()
        {            
            var isoFile = IsolatedStorageFile.GetUserStoreForApplication();
            isoFile.EnsureDirectory(IsolatedStoragePath);            

            // Get all picture files from the pictures directory and populate the Items collection.
            var pictureFiles = isoFile.GetFileNames(Path.Combine(IsolatedStoragePath, "*.jpg"));
            foreach (var pictureFile in pictureFiles)
            {
                var picture = LoadFromLocalStorage(pictureFile, IsolatedStoragePath);
                _pictures.Add(picture);
            }
        }        

        /// <summary>
        /// Load picture from application's isolated storage.
        /// </summary>
        public CapturedPicture LoadFromLocalStorage(string fileName, string directory)
        {
            var isoFile = IsolatedStorageFile.GetUserStoreForApplication();

            string filePath = Path.Combine(directory, fileName);
            using (var fileStream = isoFile.OpenFile(filePath, FileMode.Open, FileAccess.Read))
            {
                using (var reader = new BinaryReader(fileStream))
                {
                    var capturedPicture = new CapturedPicture();
                    capturedPicture.Deserialize(reader);
                    return capturedPicture;
                }
            }
        }

        /// <summary>
        /// Save picture to application's isolated storage.
        /// </summary>
        public void SaveToLocalStorage(CapturedPicture capturedPicture, string directory)
        {
            var isoFile = IsolatedStorageFile.GetUserStoreForApplication();
            isoFile.EnsureDirectory(directory);

            string filePath = Path.Combine(directory, capturedPicture.FileName);
            using (var fileStream = isoFile.CreateFile(filePath))
            {
                using (var writer = new BinaryWriter(fileStream))
                {
                    capturedPicture.Serialize(writer);
                }
            }
        }

        /// <summary>
        /// Save picture to pictures hub.
        /// </summary>
        public void SaveToPicturesHub(CapturedPicture picture)
        {
            var mediaLibrary = new MediaLibrary();
            mediaLibrary.SavePicture(picture.FileName, picture.ImageBytes);
        }
        
        #endregion

        #region Singleton Pattern

        private PictureRepository()
        {
            LoadSampleImages();
            LoadAllPicturesFromIsolatedStorage();
        }

        public static readonly PictureRepository Instance = new PictureRepository();
            
        #endregion
    }
}
